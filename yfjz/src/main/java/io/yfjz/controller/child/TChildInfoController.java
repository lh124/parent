package io.yfjz.controller.child;

import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.entity.child.*;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.service.child.*;
import io.yfjz.service.sys.SysDictService;
import io.yfjz.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 儿童基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
@Controller
@RequestMapping("/child")
public class TChildInfoController {

    @Autowired
	private TChildInfoService tChildInfoService;

    @Autowired
    private ChildInoculateService childInoculateService;

	@Autowired
	private SysDictService sysDictService;
	@Autowired
	TChildAllergyService tChildAllergyService;
	@Autowired
	TChildAbnormalService tChildAbnormalService;
	@Autowired
	private TChildTabooService tChildTabooService;
	@Autowired
	private TChildInfectionService tChildInfectionService;

	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest req,Integer page, Integer limit){
		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
		map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		//邓召仕添加金卫信条码支持
		if (map.get("chilCode") != null) {
			String chilCode =  map.get("chilCode").toString();
			if (chilCode.length() == 12){//金卫信条码
				map.put("chilBarcode",chilCode);
				map.put("chilCode","");
			}
		}
		
		//查询列表数据
		if(map.get("sidx")!=null && "chil_birthday".equals(map.get("sidx"))){
			map.put("sidx",null);
		}else{
			map.put("sidx","chil_birthday");
			map.put("order","desc");
		}
		List<TChildInfoEntity> tChildInfoList = tChildInfoService.queryList(map);
		int total = tChildInfoService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildInfoList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{chilCode}")
	public R info(@PathVariable("chilCode") String chilCode){
		TChildInfoEntity tChildInfo = tChildInfoService.queryObject(chilCode);
		
		return R.ok().put("tChildInfo", tChildInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildInfoEntity tChildInfo){
		tChildInfo.setChilCode(GetChildCodeUtil.getChildCode());
		tChildInfo.setChilCurdepartment(Constant.GLOBAL_ORG_ID);
		tChildInfo.setChilPredepartment(Constant.GLOBAL_ORG_ID);
		tChildInfo.setChilCreatesite(Constant.GLOBAL_ORG_ID);
		tChildInfo.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildInfoService.save(tChildInfo);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildInfoEntity tChildInfo){
		tChildInfo.setChilCreatesite(Constant.GLOBAL_ORG_ID);
		tChildInfoService.update(tChildInfo);
		
		return R.ok();
	}
	/**
	 * 修改儿童信息
	 */
	@ResponseBody
	@RequestMapping("/updatainfo")
	public R updatainfo(@RequestBody TChildInfoEntity tChildInfo){
		tChildInfo.setChilCreatesite(Constant.GLOBAL_ORG_ID);
		tChildInfoService.updatainfo(tChildInfo);

		return R.ok();
	}


	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] chilCodes){
		tChildInfoService.deleteBatch(chilCodes);
		
		return R.ok();
	}

	/**
	 * 根据儿童编码查询过敏史，异常反应，禁忌，传染病史
	 * @author 饶士培
	 * @date 2018-09-03
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listTrouble")
	public R listTrouble(@RequestParam Map<String,Object> map) {
		Map<String,Object> listMap = new HashMap<>();
		Query query = new Query(map);
		//查询列表数据
		List<TChildAllergyEntity> tChildAllergyList = tChildAllergyService.queryList(query);
		List<TChildAbnormalEntity> tChildAbnormalList = tChildAbnormalService.queryList(query);
		List<TChildInfectionEntity> tChildInfectionList = tChildInfectionService.queryList(query);
		List<TChildTabooEntity> tChildTabooList = tChildTabooService.queryList(query);
		listMap.put("tChildAllergyList",tChildAllergyList);
		listMap.put("tChildAbnormalList",tChildAbnormalList);
		listMap.put("tChildInfectionList",tChildInfectionList);
		listMap.put("tChildTabooList",tChildTabooList);
		//int total = tChildAllergyService.queryTotal(query);
		//PageUtils pageUtil = new PageUtils(tChildAllergyList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", listMap);
	}

	@ResponseBody
	@RequestMapping("/listinfostatus")
	public R listinfostatus(@RequestParam Map<String,Object> map, HttpServletRequest request){
		//map.put("ttype",request.getParameter("ttype"));
		List<SysDictEntity> tChildInfoList = sysDictService.selectListByType(request.getParameter("ttype"));
		return R.ok().put("data",tChildInfoList);
	}

	@ResponseBody
	@RequestMapping("/listdiccode")
	public R listnationcode(@RequestParam Map<String,Object> map, HttpServletRequest request){
		//map.put("ttype",request.getParameter("ttype"));
		List<SysDictEntity> tChildInfoList = sysDictService.selectListByType(request.getParameter("ttype"));
		return R.ok().put("data",tChildInfoList);
	}

	/**儿童基本资料完整性统计
	 * @method_name: childGather
	 * @describe:
	 * @param param, request, response]
	 * @return void
	 * @author 饶士培
	 * @QQ: 1013147559@qq.com
	 * @tel:18798010686
	 * @date: 2018-08-23  9:19
	 **/
	@RequestMapping("/childGather")
	@ResponseBody
	public R childGather(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request){
		int year_start = 0;
		int year_end = 0;
		if(request.getParameter("year_start")!=null && request.getParameter("year_start")!="") {
			year_start = Integer.parseInt(request.getParameter("year_start"));
		}
		if(request.getParameter("year_end")!=null && request.getParameter("year_end")!="") {
			year_end = Integer.parseInt(request.getParameter("year_end"));
		}
		int year = year_end-year_start;
		if(year>0){
			String[] yearStr = new String[year+1];
			int[] years = new int[year+1];
			for(int i = 0; i <= year;i++){
				years[i] = year_start+i;
				yearStr[i] = String.valueOf(years[i]);
			}
			if(null != years) {
				List yearList = Arrays.asList(yearStr);
				param.put("yearList", yearList);
			}
		}
        String[] childInfoStatus = request.getParameterValues("childInfoStatus[]");
		param.put("childInfoStatus",childInfoStatus);
        List<IntegrityRateEntity> integrityRate= tChildInfoService.listIntegrityRate(param);
		if(integrityRate!=null){
			PageUtils pageUtil = new PageUtils(integrityRate, integrityRate.size(), limit, page);
			return R.ok().put("page", pageUtil);
		}else{
			return R.error("没有查找到数据");
		}
	}

	@RequestMapping("/barCodeSave")
	@ResponseBody
	public R barCodeSave(String childCode,String barCode){
		try {
			tChildInfoService.updateBarCode(childCode,barCode);
			return R.ok("更新成功！");
		}catch (Exception e){
			return R.error();
		}
	}

	/**
	 * 儿童信息不完整明细查询
	 * @author:饶士培
	 * @date:2018-08-24
	 * @param request
	 */
	@RequestMapping("/imperfectChild")
	@ResponseBody
	protected R imperfectChild(Integer page, Integer limit,HttpServletRequest request) {
		Map<String, Object> param = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		/*Query param = new Query(params);*/
		param.put("offset", (page - 1) * limit);
		param.put("limit", limit);
        String[] childInfoStatus = request.getParameterValues("childInfoStatus[]");
        param.put("childInfoStatus",childInfoStatus);
		List<TChildInfoEntity> listImperfectChild= tChildInfoService.listImperfectChild(param);
		int total = tChildInfoService.queryTotalImperfectChild(param);
		if(listImperfectChild!=null){
			PageUtils pageUtil = new PageUtils(listImperfectChild, total, limit,page);
			return R.ok().put("page", pageUtil);
		}else{
			return R.error("没有查找到数据");
		}
	}

    /**
     * 接种信息不完整明细儿童查询
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/22 17:32
    */
    @RequestMapping("/viewInocChildInfo")
    @ResponseBody
    protected R viewInocChildInfo(final HttpServletRequest request,final Integer page, final Integer limit) {
        final Map<String, Object> param = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        final Object obj_childInfoStatus = param.get("childInfoStatus");//在册情况
        final Object obj_chilResidence = param.get("chilResidence");//居住属性
        final Object obj_chilAccount = param.get("chilAccount");//户籍属性
        final Object obj_chilCommittees = param.get("chilCommittees");//居委会/行政村
        final Map<String,Object> params = new HashMap<String,Object>();
        if(obj_childInfoStatus != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_childInfoStatus);
            } catch (Exception e) {
                list = Arrays.asList(obj_childInfoStatus.toString());
            }
            params.put("childInfoStatus",list);
        }
        if(obj_chilResidence != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilResidence);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilResidence.toString());
            }
            params.put("chilResidence",list);
        }
        if(obj_chilAccount != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilAccount);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilAccount.toString());
            }
            params.put("chil_account",list);
        }
        if(obj_chilCommittees != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilCommittees);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilCommittees.toString());
            }
            params.put("committee",list);
        }
        params.put("inocDateStart",param.get("inocDateStart"));
        params.put("inocDateEnd",param.get("inocDateEnd"));
        params.put("birthDayStart",param.get("birthDayStart"));
        params.put("birthDayEnd",param.get("birthDayEnd"));
        params.put("org_id",param.get("org_id"));
        params.put("inoc_bact_code",param.get("inoc_bact_code"));
        params.put("offset", (page - 1) * limit);
        params.put("limit", limit);
        params.put("sidx", param.get("sidx"));
        params.put("order", param.get("order"));
        params.put("child_code", param.get("child_code"));//儿童编号
        final List<Map<String,Object>> listImperfectInocChild= childInoculateService.viewInocChildInfoData(params);
        for(Map<String,Object> map : listImperfectInocChild){
            String describe = "";
            if(map.get("inocCorpCode")==null || "".equals(map.get("inocCorpCode").toString().trim())){
                describe = describe.concat("生产企业为空,");
            }
            if(map.get("inocPay")==null || "".equals(map.get("inocPay").toString().trim())){
                describe = describe.concat("收费状态为空,");
            }
            if(map.get("inocNurse")==null || "".equals(map.get("inocNurse").toString().trim())){
                describe = describe.concat("接种医生为空,");
            }
            if(map.get("inocInplId")==null || "".equals(map.get("inocInplId").toString().trim())){
                describe = describe.concat("接种部位为空,");
            }
            if(map.get("inocDepaCode")==null || "".equals(map.get("inocDepaCode").toString().trim())){
                describe = describe.concat("接种单位为空,");
            }
            if(map.get("inocBatchno")==null || "".equals(map.get("inocBatchno").toString().trim())){
                describe = describe.concat("疫苗批号为空,");
            }
            /*if(map.get("create_time")==null || "".equals(map.get("create_time").toString().trim())){
                describe = describe.concat("创建日期为空,");
            }*/
            map.put("describe",(describe.length() > 0) ? describe.substring(0,describe.length()-1) : describe);
        }
        Integer total = childInoculateService.viewInocChildInfoTotal(params);
        if(listImperfectInocChild!=null){
            final PageUtils pageUtil = new PageUtils(listImperfectInocChild, total,limit,page);
            return R.ok().put("page",pageUtil);
        }else{
            return R.error("没有查找到数据");
        }
    }

    /**
     * 查询儿童接种记录里所有疫苗列表
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/23 14:15
    */
    @ResponseBody
    @RequestMapping("/listChildInoculate")
    public R listChildInoculate(final HttpServletRequest request){
        final Map<String, Object> param = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        final List<Map<String,Object>> tVacInfoList = childInoculateService.listChildInoculateData(param);
        final PageUtils pageUtil = new PageUtils(tVacInfoList);
        return R.ok().put("page",pageUtil);
    }

	/**
	 * 今日已接种儿童
	 * @author 饶士培
	 * @date 2018-09-25
	 * @param request
	 * @return
	 */
	@RequestMapping("/currentDayInocChild")
	@ResponseBody
	protected R currentDayInocChild(Integer page, Integer limit,HttpServletRequest request) {
		Map<String, Object> param = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		param.put("offset", (page - 1) * limit);
		param.put("limit", limit);
		param.put("org_id",ShiroUtils.getUserEntity().getOrgId());
		if(param.get("sidx")!=null){
			param.put("sidx","chil_birthday");
		}
		List<TChildInfoEntity> listCurrentDayInocChild= tChildInfoService.currentDayInocChild(param);
		int total = tChildInfoService.queryTotalCurrentDayInocChild(param);
		if(listCurrentDayInocChild!=null){
			PageUtils pageUtil = new PageUtils(listCurrentDayInocChild, total, limit, page);
			return R.ok().put("page", pageUtil);
		}else{
			return R.error("没有查找到数据");
		}

	}

	/**
	 * 今日待接种儿童
	 * @author 饶士培
	 * @date 2018-09-25
	 * @param params
	 * @return
	 */
	@RequestMapping("/currentDayWaitInocChild")
	@ResponseBody
	protected R currentDayWaitInocChild(@RequestParam Map<String,Object> params) {
		Query param = new Query(params);
		param.put("org_id",ShiroUtils.getUserEntity().getOrgId());
		List<TChildInfoEntity> listCurrentDayWaitInocChild= tChildInfoService.currentDayWaitInocChild(param);
		int total = tChildInfoService.queryTotalCurrentDayWaitInocChild(param);
		if(listCurrentDayWaitInocChild!=null){
			PageUtils pageUtil = new PageUtils(listCurrentDayWaitInocChild, total, param.getLimit(), param.getPage());
			return R.ok().put("page", pageUtil);
		}else{
			return R.error("没有查找到数据");
		}

	}
	/** 
	* @Description: 获取当前机构所在的地址，默认新建档案为该地址 
	* @Param: [params] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/17 17:15
	* @Tel  17328595627
	*/ 
	@RequestMapping("/getCurrentAddress")
	@ResponseBody
	protected String getCurrentAddress() {
		return tChildInfoService.getCurrentAddress();
	}
}
