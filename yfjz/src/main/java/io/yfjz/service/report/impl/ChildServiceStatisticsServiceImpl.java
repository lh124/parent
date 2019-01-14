package io.yfjz.service.report.impl;

import io.yfjz.dao.queue.TQueueNoOperateDao;
import io.yfjz.dao.statistics.ChildServiceStatisticsDao;
import io.yfjz.service.report.ChildServiceStatisticsService;
import io.yfjz.service.report.WorkStatisticsService;
import io.yfjz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by tianjinhai on 2018/9/6 17:05
 */
@Service
public class ChildServiceStatisticsServiceImpl implements ChildServiceStatisticsService {

    @Autowired
    private ChildServiceStatisticsDao childServiceStatisticsDao;


    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {
        List<Map<String, Object>> retList = new ArrayList<>();
        List<Map<String, Object>> regList= childServiceStatisticsDao.queryRegisterList(queryMap);
        queryMap.put("childCode","");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, Object> regMap : regList) {
            Object childId = regMap.get("childId");
            Object cancel = regMap.get("cancel");
            Object step = regMap.get("step");
            if (childId!=null&&!queryMap.get("childCode").equals(childId)){
                queryMap.put("childCode",childId);
                List<Map<String, Object>> inoList= childServiceStatisticsDao.queryInoculateList(queryMap);
                retList.addAll(inoList);
            }
            if (cancel!=null&&"register".equals(step)){
                regMap.put("cancelReason",cancel);
            }else if (cancel!=null&&"inoculate".equals(step)){
                regMap.put("inoculateCancelReason",cancel);
            }
        }

        for (Map<String, Object> map : regList) {
            String  registerTime="";
            if(!StringUtils.isEmpty(map.get("registerTime"))){
                registerTime =sdf.format(map.get("registerTime"));
            }
            for (Map<String, Object> retMap : retList) {
                String inocDate ="";
                if(!StringUtils.isEmpty(retMap.get("inoculateTime"))){
                    inocDate =sdf.format((Date)retMap.get("inoculateTime"));
                }
                Object leaveTime = retMap.get("leaveTime");
                Object cancel = map.get("cancel");
                Object step = map.get("step");
                if (cancel!=null&&"inoculate".equals(step)){
                    retMap.put("inoculateTime","");
                    retMap.put("inoculateDoctor","");
                    retMap.put("inoculateVaccine","");
                    retMap.put("inoculateBatchnum","");
                    retMap.put("isObserve","");
                    break;
                }
                if (cancel!=null&&"register".equals(step)){
                    retMap.put("inoculateTime","");
                    retMap.put("inoculateDoctor","");
                    retMap.put("inoculateVaccine","");
                    retMap.put("inoculateBatchnum","");
                    retMap.put("isObserve","");
                    break;
                }
                if (retMap.get("inocCode").equals(map.get("vaccineCode"))&&registerTime.equals(inocDate)&&map.get("childId").equals(retMap.get("childCode"))){
                     map.put("inoculateTime",retMap.get("inoculateTime")==null?"":retMap.get("inoculateTime"));
                    Object childId = map.get("childId");
                    if (childId!=null){
                        queryMap.put("childCode",childId);
                        List<Map<String, Object>> inoList= childServiceStatisticsDao.queryInoculateList(queryMap);
                        for (Map<String, Object> retMap1 : inoList) {
                            if(!StringUtils.isEmpty(retMap1.get("inoculateTime"))){
                                map.put("inoculateTime",retMap1.get("inoculateTime"));
                            }
                        }
                    }
                    if (leaveTime==null){
                        map.put("isObserve","否");
                    }else{
                        long subtract = DateUtils.dateSubtract((Date) leaveTime, (Date) retMap.get("inoculateTime"));
                        if (subtract>30){
                            map.put("isObserve","是");
                        }else{
                            map.put("isObserve","否");
                        }
                    }
                    map.put("inoculateDoctor",retMap.get("inoculateDoctor"));
                    map.put("inoculateVaccine",retMap.get("inoculateVaccine"));
                    map.put("inoculateBatchnum",retMap.get("inoculateBatchnum"));
                    break;
                }
            }
        }

        return regList;
    }

    @Override
    public int queryTotal(Map<String, Object> queryMap) {
        return childServiceStatisticsDao.queryTotal(queryMap);
    }
}
