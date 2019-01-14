package io.yfjz.service.rule.impl;

import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.service.rule.CommonService;
import io.yfjz.service.rule.TRuleReplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 规则公共接口
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-09-07 11:18
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private TRuleReplaceService tRuleReplaceService;
    @Autowired
    private TRuleUnionDao tRuleUnionDao;
    @Override
    public int queryPvTotal(String childCode, int monthAge) {
        List<String> bios = new ArrayList<>();
        List<String> opvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.OPV,1);
        List<String> ipvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.IPV,1);
        if (opvList != null && !opvList.isEmpty()){
            bios.addAll(opvList);
        }
        if (ipvList != null && !ipvList.isEmpty()){
            bios.addAll(ipvList);
        }
        Map<String,Object> param = new HashMap<>();
        param.put("childId",childCode);
        param.put("bios",bios);
        return tRuleUnionDao.queryTotalByBioTime(param);
    }

    @Override
    public int queryUnBOPVTotal(String childCode, int monthAge) {
        List<String> bios = new ArrayList<>();
        List<String> opvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.OPV,1);
        List<String> ipvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.IPV,1);
        if (opvList != null && !opvList.isEmpty()){
            bios.addAll(opvList);
        }
        if (ipvList != null && !ipvList.isEmpty()){
            bios.addAll(ipvList);
        }
        Iterator<String> iterator = bios.iterator();
        while (iterator.hasNext()){
            String bioId = iterator.next();
            if ("0311".equals(bioId)) iterator.remove();
        }
        Map<String,Object> param = new HashMap<>();
        param.put("childId",childCode);
        param.put("bios",bios);
        return tRuleUnionDao.queryTotalByBioTime(param);
    }

    @Override
    public int queryIPVTotal(String childCode, int monthAge) {
        List<String> bios = new ArrayList<>();
        List<String> ipvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.IPV,1);
        if (ipvList != null && !ipvList.isEmpty()){
            bios.addAll(ipvList);
        }
        Map<String,Object> param = new HashMap<>();
        param.put("childId",childCode);
        param.put("bios",bios);
        param.put("property","yes");
        return tRuleUnionDao.queryTotalByBioTime(param);
    }

    @Override
    public int queryDtapDtTotal(String childCode, int monthAge) {
        List<String> bios = new ArrayList<>();
        List<String> opvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.DTaP,1);
        List<String> ipvList = tRuleReplaceService.selectReplaceCodeNoAge(PlanDicContext.DT,1);
        if (opvList != null && !opvList.isEmpty()){
            bios.addAll(opvList);
        }
        if (ipvList != null && !ipvList.isEmpty()){
            bios.addAll(ipvList);
        }
        Map<String,Object> param = new HashMap<>();
        param.put("childId",childCode);
        param.put("bios",bios);
        return tRuleUnionDao.queryTotalByBioTime(param);
    }
}
