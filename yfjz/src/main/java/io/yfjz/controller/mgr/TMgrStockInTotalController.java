package io.yfjz.controller.mgr;

import io.yfjz.entity.mgr.TMgrStockInTotalEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.mgr.TMgrStockInTotalService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 入库汇总
* @Param:
* @return:
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/20 9:09
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("tmgrstockintotal")
public class TMgrStockInTotalController {

    @Autowired
    private TMgrStockInTotalService totalService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(Integer page, Integer limit,String searchOrder,String searchUser,String startDate,String endDate) {

        if(StringUtils.isEmpty(page)&&StringUtils.isEmpty(limit)){
            page=1;
            limit=10;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        map.put("searchOrder",searchOrder);
        map.put("searchUser",searchUser);
        map.put("type", Constant.IN_TYPE_NORMAL);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!StringUtils.isEmpty(startDate)){
                Date start = sdf.parse(startDate);
                map.put("startDate",start);
            }
            if (!StringUtils.isEmpty(endDate)){
                Date end = sdf.parse(endDate);
                Date date = DateUtils.addDays(end, 1);
                map.put("endDate",date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //查询列表数据
        List<TMgrStockInTotalEntity> tMgrStoreList = totalService.queryList(map);
        int total = totalService.queryTotal(map);

        PageUtils pageUtil = new PageUtils(tMgrStoreList, total, limit, page);

        return R.ok().put("page", pageUtil);
    }
    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/itemList/{totalId}")
    public R itemList(Integer page, Integer limit, @PathVariable("totalId") String totalId){
        if(StringUtils.isEmpty(page)&&StringUtils.isEmpty(limit)){
            page=1;
            limit=10;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        map.put("totalId",totalId);
        //查询列表数据
        List<Map<String,Object>> tMgrStoreList = totalService.queryItemList(map);
        int total = totalService.queryItemTotal(map);

        PageUtils pageUtil = new PageUtils(tMgrStoreList, total, limit, page);

        return R.ok().put("page", pageUtil);
    }
    /**
     * 打印库存
     */
    @ResponseBody
    @RequestMapping("/print")
    public void print(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "ids", required = false)String ids){
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //获取Jasper文件路径
        String targetPath = request.getRealPath("/");
        String filepath = targetPath +"reports/stockInList.jrxml";
//        System.out.println(ids);
        //获取JasperReport对象
        JasperReport jreport = null;
        try {
        jreport = JasperCompileManager.compileReport(filepath);
        Map<String, Object> params = new HashMap<String,Object>();
        //把数据放到Map中
//        String id="f3f8ba3b-a237-4bfc-8a5b-2d34fa6d5b48";
        TMgrStockInTotalEntity entity=  totalService.queryInfoById(ids);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("nowDate",sdf.format(new Date()));
        params.put("doctorName", userEntity.getRealName()==null?"":userEntity.getRealName());
        params.put("stockInCode", entity.getStockInCode());
        params.put("stockUser", entity.getFkInStockUser());
        params.put("storageTime", sdf.format(entity.getStorageTime()));
        params.put("store", entity.getStore().getName());
        params.put("remark", entity.getRemark()==null?"":entity.getRemark());
        //查询入库明细
        Map<String, Object> map = new HashMap<>();
        map.put("totalId",ids);
        List<Map<String, Object>> list = totalService.queryItemList(map);
        JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
        // print对象
        JasperPrint print = JasperFillManager.fillReport(jreport, params,
                dataSource);
        response.setContentType("application/pdf");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "inline; filename="
                + new String("Download".getBytes("utf-8")) + ".pdf" + "");

        JRAbstractExporter exporter = new JRPdfExporter();
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                response.getOutputStream());
        ExporterInput exporterInput = new SimpleExporterInput(print);

        exporter.setExporterOutput(exporterOutput);
        // 设置输入项
        exporter.setExporterInput(exporterInput);
        exporter.exportReport();
        exporterOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改入库数量
     */
    @ResponseBody
    @RequestMapping("/updateAmount")
    public Map updateAmount(@RequestParam Map map){
        try {
            totalService.updateAmount(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().put("msg","修改失败，系统异常！");
        }
        return R.ok().put("msg","修改成功！");
    }
    /** 
    * @Description: 查询修改记录列表 
    * @Param: [page, limit, searchOrder, searchUser, startDate, endDate] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/8 9:42
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/modifyList")
    public R getModifyList(Integer page, Integer limit,String modifyUser,String vaccine,String startDate,String endDate) {

        if(StringUtils.isEmpty(page)&&StringUtils.isEmpty(limit)){
            page=1;
            limit=10;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        map.put("modifyUser",modifyUser);
        map.put("vaccine",vaccine);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            if (!StringUtils.isEmpty(startDate)){
                Date start = sdf.parse(startDate);
                map.put("startDate",start);
            }
            if (!StringUtils.isEmpty(endDate)){
                Date end = sdf.parse(endDate);
                Date date = DateUtils.addDays(end, 1);
                map.put("endDate",date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //查询列表数据
        List<Map<String,Object>> tMgrStoreList = totalService.getModifyList(map);
        int total = totalService.getModifyListTotal(map);

        PageUtils pageUtil = new PageUtils(tMgrStoreList, total, limit, page);

        return R.ok().put("page", pageUtil);
    }
}
