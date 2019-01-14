package io.yfjz.controller.bus;

import io.yfjz.service.bus.ReturnService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tianjinhai
 * @date  2018/8/15 15:44
 */
@Controller
@RequestMapping("return")
public class ReturnVaccineController {
    @Autowired
    private ReturnService returnService;
    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(Integer page, Integer limit,String type,String searchProductName,String searchFactoryName,String searchBatch,String  searchDate, Integer searchType,String storeId){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            if(!StringUtils.isEmpty(searchProductName)){
                map.put("searchProductName", searchProductName);
            }
            if(!StringUtils.isEmpty(searchFactoryName)){
                map.put("searchFactoryName", searchFactoryName);
            }
            if(!StringUtils.isEmpty(searchBatch)){
                map.put("searchBatch", searchBatch);
            }
            //查询的类型1 为领苗查询，不显示库存为0的数据
            if(!StringUtils.isEmpty(type)){
                map.put("type", type);
            }
            if(!StringUtils.isEmpty(searchDate)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(searchDate);
                Date addDays = DateUtils.addDays(date, 1);
                map.put("searchDate", addDays);
            }
            if(searchType!=null){
                map.put("searchType", searchType);
            }
            if(storeId!=null){
                map.put("storeId", storeId);
            }
            //查询列表数据
            List<Map<String, Object>> list = returnService.queryStockList(map);
            int total = returnService.queryStockTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (ParseException e) {

            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }
    
    /** 
    * @Description: 归还疫苗操作
    * @Param: [param] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/20 15:17
    * @Tel  17328595627
    */ 
    @RequestMapping("save")
    @ResponseBody
    public R saveReturnVac(@RequestBody Map param){
        try {
            int result=returnService.saveReturnVac(param);
        } catch (Exception e) {
            e.printStackTrace();
            return  R.error("归还失败！系统异常");
        }
        return R.ok().put("msg","归还成功！");
    }

    /** 
    * @Description: 查询归还的疫苗汇总
    * @Param: [page, limit] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/21 16:09
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/returnTotalList")
    public R returnTotalList(Integer page, Integer limit){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);

            //查询列表数据
            List<Map<String, Object>> list = returnService.queryReturnTotalList(map);
            int total = returnService.queryReturnTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }

    /** 
    * @Description: 查询汇总明细 
    * @Param: [page, limit, rowId] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/21 16:11
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/returnItemList/{rowId}")
    public R returnItemList(Integer page, Integer limit, @PathVariable("rowId") String rowId){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            map.put("totalId", rowId);

            //查询列表数据
            List<Map<String, Object>> list = returnService.queryReturnItemList(map);
            int total = returnService.queryReturnItemTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }
}
