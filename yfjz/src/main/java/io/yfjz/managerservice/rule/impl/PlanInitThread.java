package io.yfjz.managerservice.rule.impl;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.service.rule.TRulePlanConsultService;
import io.yfjz.utils.SpringContextUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 初始化儿童规划接种信息
 * 
 * @author 邓召仕2018-5-7
 * 
 */
public class PlanInitThread implements Runnable {
	private static Logger log = Logger.getLogger(PlanInitThread.class);
	//spring 容器
	public static  ApplicationContext context=null;
	//小孩信息
	private ChildData child;
	//*规划参照信息
	private List<TRulePlanConsultEntity> consuls;
	//规划组件
	private TRulePlanConsultService tRulePlanConsultService;


	public PlanInitThread(ChildData child, List<TRulePlanConsultEntity> consuls) {
		this.child = child;
		this.consuls = consuls;
	}
	public PlanInitThread() {
	}

	@Override
	public void run() {
		try {
			if(child !=null && consuls != null){
				tRulePlanConsultService = (TRulePlanConsultService) SpringContextUtils.getBean("tRulePlanConsultService");
				List<TRulePlanConsultEntity> plans = tRulePlanConsultService.getNowPlanList(child,consuls);
				tRulePlanConsultService.batchSaveConsultToPlan(plans);//保存计划后信息到数据库
				log.info("一个线程执行完毕，已添加该儿童规划信息");
				InitPlanServiceImpl.childNumber ++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
