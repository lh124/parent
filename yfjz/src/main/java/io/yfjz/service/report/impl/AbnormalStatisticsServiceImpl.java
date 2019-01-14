package io.yfjz.service.report.impl;

import io.yfjz.dao.statistics.AbnormalStatisticsDao;
import io.yfjz.dao.statistics.ChildServiceStatisticsDao;
import io.yfjz.service.report.AbnormalStatisticsService;
import io.yfjz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * create by tianjinhai on 2018/9/26 11:03
 */
@Service
public class AbnormalStatisticsServiceImpl implements AbnormalStatisticsService {
    @Autowired
    private AbnormalStatisticsDao abnormalDao;

    @Override
    public List<Map<String, Object>> queryList(Map queryMap) {
        Object abnormalStatus = queryMap.get("abnormalStatus");
        List<Map<String, Object>>  list =new ArrayList<>();
        //查询最近一个月的异常数据
        if (abnormalStatus==null){
            //查询取号未登记儿童
            queryMap.put("abnormalStatus","register");
            List<Map<String, Object>> registerList = abnormalDao.queryList(queryMap);
            //查询取消原因
            List<Map<String, Object>> registerCancelList = abnormalDao.queryCancelReason(registerList);
            for (Map<String, Object> regMap : registerList) {
                Object id = regMap.get("id");
                for (Map<String, Object> cancelMap : registerCancelList) {
                    Object queueId = cancelMap.get("queueId");
                    if (id.equals(queueId)){
                        regMap.put("abStatus","取号未登记");
                        regMap.put("abnormalDetail",cancelMap.get("remark"));
                        break;
                    }
                }
            }
            list.addAll(registerList);
            //查询登记未接种儿童
            queryMap.put("abnormalStatus","inoculate");
            List<Map<String, Object>> inoculateList = abnormalDao.queryList(queryMap);
            //查询取消原因
            List<Map<String, Object>> inoculateCancelList = abnormalDao.queryCancelReason(inoculateList);
            for (Map<String, Object> inoMap : inoculateList) {
                Object id = inoMap.get("id");
                for (Map<String, Object> cancelMap : inoculateCancelList) {
                    Object queueId = cancelMap.get("queueId");
                    if (id.equals(queueId)){
                        inoMap.put("abStatus","登记未接种");
                        inoMap.put("abnormalDetail",cancelMap.get("remark"));
                        break;
                    }
                }
            }
            list.addAll(inoculateList);
            //查询接种未留观
            queryMap.put("abnormalStatus","observe");
            List<Map<String, Object>> observeList = abnormalDao.queryList(queryMap);
            List<Map<String, Object>> tempList = new ArrayList<>();
            //查询儿童当天的留观时间
            for (Map<String, Object> obMap : observeList) {
                List<Map<String, Object>> leaveTimeList= abnormalDao.queryLeaveTime(obMap);
                if (leaveTimeList.size()>0) {
                    Map<String, Object> leaveMap = leaveTimeList.get(0);
                    Object diffTime = leaveMap.get("diffTime");
                    if (diffTime==null){
                        obMap.put("abStatus","接种未留观");
                        obMap.put("abnormalDetail","接种未留观");
                        tempList.add(obMap);
                    }else if(Integer.valueOf(diffTime.toString())<30){
                        obMap.put("abStatus","留观未完成");
                        obMap.put("abnormalDetail","留观未完成");
                        tempList.add(obMap);
                    }
                }
            }
            list.addAll(tempList);
        }else if (abnormalStatus.equals("register")||abnormalStatus.equals("inoculate")){
            List<Map<String, Object>> registerList = abnormalDao.queryList(queryMap);
            //查询取消原因
            List<Map<String, Object>> registerCancelList = abnormalDao.queryCancelReason(registerList);
            for (Map<String, Object> regMap : registerList) {
                Object id = regMap.get("id");
                for (Map<String, Object> cancelMap : registerCancelList) {
                    Object queueId = cancelMap.get("queueId");
                    if (id.equals(queueId)){
                        if (abnormalStatus.equals("register")){
                            regMap.put("abStatus","取号未登记");
                        }else{
                            regMap.put("abStatus","登记未接种");
                        }
                        regMap.put("abnormalDetail",cancelMap.get("remark"));
                        break;
                    }
                }
            }
            list.addAll(registerList);
        }else if (abnormalStatus.equals("observe")){
            List<Map<String, Object>> observeList = abnormalDao.queryList(queryMap);
            List<Map<String, Object>> tempList = new ArrayList<>();
            //查询儿童当天的留观时间
            for (Map<String, Object> obMap : observeList) {
                List<Map<String, Object>> leaveTimeList= abnormalDao.queryLeaveTime(obMap);
                if (leaveTimeList.size()>0) {
                    Map<String, Object> leaveMap = leaveTimeList.get(0);
                    Object diffTime = leaveMap.get("diffTime");
                    if(diffTime!=null&&Integer.valueOf(diffTime.toString())<30){
                        obMap.put("abStatus","留观未完成");
                        obMap.put("abnormalDetail","留观未完成");
                        tempList.add(obMap);
                    }
                }
            }
            list.addAll(tempList);
        }else  if (abnormalStatus.equals("inoculateNotAbserve")){
            queryMap.put("abnormalStatus","observe");
            List<Map<String, Object>> observeList = abnormalDao.queryList(queryMap);
            List<Map<String, Object>> tempList = new ArrayList<>();
            //查询儿童当天的留观时间
            for (Map<String, Object> obMap : observeList) {
                List<Map<String, Object>> leaveTimeList= abnormalDao.queryLeaveTime(obMap);
                if (leaveTimeList.size()>0) {
                    Map<String, Object> leaveMap = leaveTimeList.get(0);
                    Object diffTime = leaveMap.get("diffTime");
                    if (diffTime==null){
                        obMap.put("abStatus","接种未留观");
                        obMap.put("abnormalDetail","接种未留观");
                        tempList.add(obMap);
                    }
                }
            }
            list.addAll(tempList);
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String abStatus1 = (String)o1.get("abStatus");
                String abStatus2 = (String)o2.get("abStatus");
                return abStatus1.compareTo(abStatus2);
            }
        });
        return list;
    }

    @Override
    public int queryTotal(Map queryMap) {
        return abnormalDao.queryTotal(queryMap);
    }
}
