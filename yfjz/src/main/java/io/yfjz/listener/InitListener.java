package io.yfjz.listener;

import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.websocket.TowerSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/** 
* @Description: spring监听器
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/28 12:00
* @Tel  17328595627
*/ 

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TBaseTowerService towerService;
    @Autowired
    private RuleCache ruleCache;

    /**
    * @Description: spring启动的时候触发,
    * @Param: [contextRefreshedEvent] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 12:00
    * @Tel  17328595627
    */ 
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initTowers();
    }
    /** 
    * @Description: 初始化工作台数据，加入缓存中
    * @Param: [] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 12:03
    * @Tel  17328595627
    */ 
    private void initTowers() {
        List<String> towers=towerService.getAllTowers();
        TowerSessionUtils.towers=towers;
        ruleCache.finshRuleCache(true);
    }


}
