package io.yfjz.controller.bus;


import io.yfjz.entity.bus.TBusEvaluateEntity;
import io.yfjz.service.bus.TBusEvaluateService;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;


/**
 * 用户评价
 * @describe:
 * @param 
 * @return 
 * @author 邓召仕
 * @date: 2018-09-05  17:42
 **/
@Controller
@RequestMapping("tbusevaluate")
public class TBusEvaluateController {
	@Autowired
	private TBusEvaluateService tBusEvaluateService;
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(String childId,String evaluateType){
		//数据库设计与前台值刚好相反，这里做出处理
		Integer pjState = null;
		if("1".equals(evaluateType)){
			pjState = 4;
		}else if("2".equals(evaluateType)){
			pjState = 3;
		}else if("3".equals(evaluateType)){
			pjState = 2;
		}else if("4".equals(evaluateType)){
			pjState = 1;
		}

		String userId = ShiroUtils.getUserEntity().getUserId();
		String orgId = ShiroUtils.getUserEntity().getOrgId();
		String towerId = ShiroUtils.getUserEntity().getInoculateTowerId();
		TBusEvaluateEntity tBusEvaluate = new TBusEvaluateEntity();
		tBusEvaluate.setId(UUID.randomUUID().toString());
		tBusEvaluate.setCreateTime(new Date());
		tBusEvaluate.setEvaluateType(pjState);
		tBusEvaluate.setFkDoctorId(userId);
		tBusEvaluate.setFkChildCode(childId);
		tBusEvaluate.setOrgid(orgId);
		tBusEvaluate.setTowerId(towerId);
		tBusEvaluate.setStatus(0);
		tBusEvaluateService.save(tBusEvaluate);
		return R.ok();
	}
	

}
