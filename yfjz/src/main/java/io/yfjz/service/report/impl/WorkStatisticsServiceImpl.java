package io.yfjz.service.report.impl;

import io.yfjz.dao.queue.TQueueNoOperateDao;
import io.yfjz.service.report.WorkStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * create by tianjinhai on 2018/9/6 17:05
 */
@Service
public class WorkStatisticsServiceImpl implements WorkStatisticsService {

    @Autowired
    private TQueueNoOperateDao operateDao;


    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {
        /**
         * 完成，取消人数
         */
        List<Map<String, Object>> amountMap=operateDao.queryListByWork(queryMap);
        /**
         * 接种服务评价
         */
        List<Map<String, Object>> service = operateDao.queryService(queryMap);
        //拼接参数

        List<Map<String,Object>> cancelList = new ArrayList<>();
        if (amountMap.size()>0){
            for (Map<String, Object> map : amountMap) {

                String towerName = map.get("towerName").toString();
                String opeType = map.get("opeType").toString();
                if (map.get("realName")==null){
                    continue;
                }
                String realName = map.get("realName").toString();
                if (opeType.equals("FINISH")){
                    //总评价数
                    BigDecimal totalAmount= BigDecimal.ZERO;
                    //满意人数
                    BigDecimal satisfiedAmount=BigDecimal.ZERO;
                    for (Map<String, Object> serviceMap : service) {

                        String serviceTowerName = serviceMap.get("towerName").toString();
                        String serviceUsername = serviceMap.get("realName").toString();

                        Integer evaType = Integer.valueOf(serviceMap.get("evaType").toString());
                        BigDecimal amount = new BigDecimal(serviceMap.get("amount").toString());


                        if (opeType.equals("FINISH") &&
                                towerName.equals(serviceTowerName)
                                && realName.endsWith(serviceUsername)) {
                            totalAmount = totalAmount.add(amount);
                            switch (evaType) {
                                case 4:
                                    map.put("verySatisfied", serviceMap.get("amount"));
                                    satisfiedAmount = satisfiedAmount.add(amount);
                                    break;
                                case 3:
                                    map.put("satisfied", serviceMap.get("amount"));
                                    satisfiedAmount = satisfiedAmount.add(amount);
                                    break;
                                case 2:
                                    map.put("commonly", serviceMap.get("amount"));
                                    satisfiedAmount = satisfiedAmount.add(amount);
                                    break;
                                case 1:
                                    map.put("dissatisfied", serviceMap.get("amount"));
                                    break;
                            }
                        }
                    }
                    //计算满意率
                    if (totalAmount.compareTo(BigDecimal.ZERO)==0){
                        Double ret=0d;
                       // map.put("satisfiedRate",ret+"%" );
                        map.put("finish", map.get("amount"));
                    }else{
                        BigDecimal rate = satisfiedAmount.divide(totalAmount,4,BigDecimal.ROUND_HALF_UP);
                        Double ret=Double.valueOf(rate.toString())*100d;
                        map.put("satisfiedRate",ret+"%" );
                        map.put("finish", map.get("amount"));
                    }
                }else{
                    map.put("cancel",map.get("amount"));
                    cancelList.add(map);
                }

            }
            Iterator<Map<String, Object>> it = amountMap.iterator();
            while(it.hasNext()){
                Map<String, Object> next = it.next();
                String towerName = next.get("towerName").toString();
                if (next.get("realName")==null){
                    continue;
                }
                String realName = next.get("realName").toString();
                String opeType = next.get("opeType").toString();
                if (opeType.equals("FINISH")){
                    for (Map<String, Object> cancelMap : cancelList) {
                        String cancelTowerName = cancelMap.get("towerName").toString();
                        String cancelUsername = cancelMap.get("realName").toString();
                        if (towerName.equals(cancelTowerName)&&realName.equals(cancelUsername)){
                            next.put("cancel",cancelMap.get("amount"));
                        }

                    }
                }else{
                    it.remove();
                }

            }
            return amountMap;
        }else{
            return Collections.EMPTY_LIST;
        }



    }
}
