package io.yfjz.controller.mgr;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.service.rule.TRulePlanService;
import io.yfjz.utils.CommonUtils;
import io.yfjz.utils.ShiroUtils;
import io.yfjz.utils.pdf.PDFUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** 
* @Description: 库存信息Controller 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 16:47
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("tmgrstockinfo")
public class TMgrStockInfoController {

	@Autowired
	private TMgrStockInfoService tMgrStockInfoService;
	@Autowired
	private TRulePlanService tRulePlanService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tmgrstockinfo:list")
	public R list(Integer page, Integer limit,String type,
				  String searchProductName,
				  String searchFactoryName,
				  String searchBatch,
				  String  searchDate,
				  Integer searchType,
				  String storeId,
				  String selectType){
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
			if(StringUtils.isEmpty(selectType)){
				map.put("selectType", "hideOverdue");
			}else{
				map.put("selectType", selectType);
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
			List<Map<String, Object>> list = tMgrStockInfoService.queryList(map);
			int total = tMgrStockInfoService.queryTotal(map);

			pageUtil = new PageUtils(list, total, limit, page);
		} catch (ParseException e) {

			e.printStackTrace();
			return R.error("系统异常，请联系管理员");
		}

		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tmgrstockinfo:info")
	public R info(@PathVariable("id") String id){
		TMgrStockInfoEntity tMgrStockInfo = tMgrStockInfoService.queryObject(id);
		
		return R.ok().put("tMgrStockInfo", tMgrStockInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("tmgrstockinfo:save")
	public R save(@RequestBody Map paramMap){

		int save = 0;
		try {
			save = tMgrStockInfoService.save(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return  R.error("保存失败");
		}
		if (save==1){
			return  R.ok();
		}
		return  R.error("保存失败");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tmgrstockinfo:update")
	public R update(@RequestBody TMgrStockInfoEntity tMgrStockInfo){
		tMgrStockInfoService.update(tMgrStockInfo);
		
		return R.ok();
	}
	/** 
	* @Description: 库存报损操作 
	* @Param: [tMgrStockInfo] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/4 9:40
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/damage")
	public R damage(@RequestBody Map param){
		int damage = tMgrStockInfoService.damage(param);
		if (damage==1){
			return R.ok();
		}else{
			return R.error("报损失败");
		}
	}
	/** 
	* @Description: 库存退货操作
	* @Param: [param] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/9/8 11:24
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/return")
	public R returnGoods(@RequestBody Map param){
		System.out.println(param);
		int returnGoods = tMgrStockInfoService.returnGoods(param);
		if (returnGoods==1){
			return R.ok();
		}else{
			return R.error("退货失败");
		}
	}
	/**
	 * @Description: 获取入库单号，盘点单号
	 * @Param: [param]
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/9/8 11:24
	 * @Tel  17328595627
	 */
	@ResponseBody
	@RequestMapping("/getOrderNumber")
	public String getOrderNumber(String type){
		String orderId= tMgrStockInfoService.getOrderNumber(type);
		return  orderId;
	}
	/** 
	* @Description: 登记台库存信息列表
	* @Param: [page, limit] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/16 16:09
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/registerList")
	public R registerList(Integer page, Integer limit,String childCode,String show){
		PageUtils pageUtil = null;

		Map<String, Object> map = new HashMap<>();
		/*map.put("offset", (page - 1) * limit);
		map.put("limit", limit);*/
		//查询列表数据
		List<Map<String, Object>> list = tMgrStockInfoService.queryRegisterList(map);

		//int total = tMgrStockInfoService.queryRegisterTotal(map);
//		if(StringUtils.isBlank(show)){
//			List<Map<String, Object>> list2 = new ArrayList<>();
//			List<String> erto = tRulePlanService.getLaterBios(childCode);
//			for(Map<String,Object> map1 : list){
//				if(erto.contains(map1.get("vaccineCode"))){
////					System.out.println("移除：" + map1);
//				}else{
//					list2.add(map1);
//				}
//			}
//			pageUtil = new PageUtils(list2, 0, limit, page);
//		}else{
//			pageUtil = new PageUtils(list, 0, limit, page);
//		}

		pageUtil = new PageUtils(list, 0, limit, page);
		return R.ok().put("page", pageUtil);
	}
	/** 
	* @Description: 查询收发记录
	* @Param: [page, limit, childCode, show] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/26 19:05
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/queryDispatchList")
	public R queryDispatchList(Integer page, Integer limit,String infoId){
		PageUtils pageUtil = null;

		Map<String, Object> map = new HashMap<>();
		map.put("infoId",infoId);
		List<Map<String, Object>> list = tMgrStockInfoService.queryDispatchList(map);

		pageUtil=new PageUtils(list,list.size(),list.size(),1);

		return R.ok().put("page", pageUtil);
	}
	/** 
	* @Description: 打印收发登记表
	* @Param: [page, limit, infoId] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/26 20:52
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/printDispatch")
	public void printDispatch(HttpServletResponse response, HttpServletRequest request,Integer page, Integer limit, @RequestParam Map<String,Object> params){

		Map<String, Object> map = new HashMap<>();
		map.put("infoId",params.get("infoId"));
        //查询统计数据
        try {
            List<Map<String, Object>> list = tMgrStockInfoService.queryDispatchList(map);

            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //获取Jasper文件路径
            String path = request.getRealPath("/");
            String filepath = path +"reports/dispatch.jrxml";
            //获取JasperReport对象
            JasperReport jreport = JasperCompileManager.compileReport(filepath);
            //把数据放到Map中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("nowDate",sdf.format(new Date()));
            JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
            // print对象
            JasperPrint print = JasperFillManager.fillReport(jreport, params,
                    dataSource);
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "inline; filename="
                    + new String("疫苗出入库统计".getBytes("UTF-8")) + ".pdf" + "");

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
	* @Description: 导出收发登记表 
	* @Param: [page, limit, infoId] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/26 20:53
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/excelDispatch")
	public void excelDispatch(HttpServletResponse response, HttpServletRequest request,Integer page, Integer limit, @RequestParam Map<String,Object> params){

		Map<String, Object> map = new HashMap<>();
        map.put("infoId",params.get("infoId"));
		List<Map<String, Object>> list = tMgrStockInfoService.queryDispatchList(map);
        try{
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(15);
            HSSFRow row0 = sheet.createRow(0);
            HSSFCell cell0 = row0.createCell(0);
            //设置标题样式
            HSSFCellStyle titleStyle = wb.createCellStyle();
            HSSFFont font = wb.createFont();
//            font.setBold(true);
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 25);
            titleStyle.setFont(font);
             titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置边框:
            HSSFCellStyle setBorder = wb.createCellStyle();
            setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

            cell0.setCellValue("贵州省生物制品收发登记表");
            cell0.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 17));
            HSSFRow row2 = sheet.createRow(2);
            row2.createCell(13).setCellValue("疫苗名称：");
            row2.createCell(14).setCellValue(params.get("vaccine").toString());
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 15));
            HSSFRow row3 = sheet.createRow(3);
            row3.createCell(10).setCellValue("规格：");
            row3.createCell(11).setCellValue(params.get("dosenorm").toString());
            row3.createCell(13).setCellValue("剂型：");
            row3.createCell(14).setCellValue(params.get("drug").toString());
            row3.createCell(16).setCellValue("剂型：");
            row3.createCell(17).setCellValue(params.get("doseminUnitCode").toString());
            HSSFRow row4 = sheet.createRow(4);
            row4.createCell(0).setCellValue("日期");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0));
            row4.createCell(1).setCellValue("摘要(来源/去向)");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 1, 1));
            row4.createCell(2).setCellValue("生产企业");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 2, 2));
            row4.createCell(3).setCellValue("批准文号");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 3, 3));
            row4.createCell(4).setCellValue("批签发合格证编号");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 4, 4));
            HSSFRow row5 = sheet.createRow(5);
            row4.createCell(5).setCellValue("收入");
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 8));
            row5.createCell(5).setCellValue("数量(支)");
            row5.createCell(6).setCellValue("数量(人份)");
            row5.createCell(7).setCellValue("批号");
            row5.createCell(8).setCellValue("失效期");

            row4.createCell(9).setCellValue("发出");
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 9, 12));

            row5.createCell(9).setCellValue("数量(支)");
            row5.createCell(10).setCellValue("数量(人份)");
            row5.createCell(11).setCellValue("批号");
            row5.createCell(12).setCellValue("失效期");

            row4.createCell(13).setCellValue("结余");
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 13, 16));

            row5.createCell(13).setCellValue("数量(支)");
            row5.createCell(14).setCellValue("数量(人份)");
            row5.createCell(15).setCellValue("批号");
            row5.createCell(16).setCellValue("失效期");

            row4.createCell(17).setCellValue("备注(领苗人)");
            sheet.addMergedRegion(new CellRangeAddress(4, 5, 17, 17));
            for (int j = 0; j < 18; j++) {
                HSSFCell cell = row4.getCell(j);
                HSSFCell cell5 = row5.getCell(j);
                if (cell == null) {
                    cell = row4.createCell(j);
                }
                if (cell5 == null) {
                    cell5 = row5.createCell(j);
                }
                cell.setCellStyle(setBorder);
                cell5.setCellStyle(setBorder);

            }
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow row = sheet.createRow(6 + i);
                    Map<String, Object> itemMap = list.get(i);
                    row.createCell(0).setCellValue(itemMap.get("createTime") == null ? "" : itemMap.get("createTime").toString());
                    row.createCell(1).setCellValue(itemMap.get("storeName") == null ? "" : itemMap.get("storeName").toString());
                    row.createCell(2).setCellValue(itemMap.get("factoryName") == null ? "" : itemMap.get("factoryName").toString());
                    row.createCell(3).setCellValue(itemMap.get("licenseNumber") == null ? "" : itemMap.get("licenseNumber").toString());
                    row.createCell(4).setCellValue(itemMap.get("lotRelease") == null ? "" : itemMap.get("lotRelease").toString());
                    row.createCell(5).setCellValue(itemMap.get("inAmount") == null ? "" : itemMap.get("inAmount").toString());
                    row.createCell(6).setCellValue(itemMap.get("inPersonAmount") == null ? "" : itemMap.get("inPersonAmount").toString());
                    row.createCell(7).setCellValue(itemMap.get("inBatchnum") == null ? "" : itemMap.get("inBatchnum").toString());
                    row.createCell(8).setCellValue(itemMap.get("inExpiryDate") == null ? "" : itemMap.get("inExpiryDate").toString());
                    row.createCell(9).setCellValue(itemMap.get("outAmount") == null ? "" : itemMap.get("outAmount").toString());
                    row.createCell(10).setCellValue(itemMap.get("outPersonAmount") == null ? "" : itemMap.get("outPersonAmount").toString());
                    row.createCell(11).setCellValue(itemMap.get("outBatchnum") == null ? "" : itemMap.get("outBatchnum").toString());
                    row.createCell(12).setCellValue(itemMap.get("outExpiryDate") == null ? "" : itemMap.get("outExpiryDate").toString());
                    row.createCell(13).setCellValue(itemMap.get("remainAmount") == null ? "" : itemMap.get("remainAmount").toString());
                    row.createCell(14).setCellValue(itemMap.get("remainPersonAmount") == null ? "" : itemMap.get("remainPersonAmount").toString());
                    row.createCell(15).setCellValue(itemMap.get("remainBatchnum") == null ? "" : itemMap.get("remainBatchnum").toString());
                    row.createCell(16).setCellValue(itemMap.get("remainExpiryDate") == null ? "" : itemMap.get("remainExpiryDate").toString());
                    row.createCell(17).setCellValue(itemMap.get("realName") == null ? "" : itemMap.get("realName").toString());
                    for (int j = 0; j < 18; j++) {
                        row.getCell(j).setCellStyle(setBorder);
                    }
                }

            }
            OutputStream output = response.getOutputStream();
            System.out.println("output=" + output);
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String("贵州生物制品收发登记表.xls".getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
