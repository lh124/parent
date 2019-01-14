package io.yfjz.service.report.impl;

import groovy.util.IFileNameFinder;
import io.yfjz.dao.mgr.TMgrCheckDao;
import io.yfjz.dao.mgr.VaccineChangeDao;
import io.yfjz.entity.mgr.TMgrCheckItemEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.service.report.VaccineChangeService;
import io.yfjz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by tianjinhai on 2018/9/8 16:16
 */
@Service
public class VaccineChangeServiceImpl implements VaccineChangeService {
    @Autowired
    private VaccineChangeDao vaccineChangeDao;


    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {
        //判断查询时间是否是当年当月
        Integer year = Integer.valueOf(queryMap.get("year").toString());
        Integer month = Integer.valueOf(queryMap.get("month").toString());
        String startDate = DateUtils.getFirstDayOfMonth(year, month);
        String endDate = DateUtils.getLastDayOfMonth(year, month);
        queryMap.put("startDate",startDate);
        queryMap.put("endDate",endDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowDate = sdf.format(new Date());
        List<Map<String, Object>> infoList=null;
        //查询上月结余库存
        List<Map<String, Object>> vacs=vaccineChangeDao.queryBeforeMonth(queryMap);
        if ((year+""+month).equals(nowDate)){
           //查询当前库存
            infoList = vaccineChangeDao.queryInfoListMap(queryMap);
            for (Map<String, Object> map : infoList) {
                for (Map<String, Object> vac : vacs) {
                    if (vac.get("baseId").equals(map.get("baseId"))){
                        map.put("initAmount",vac.get("amount"));
                        break;
                    }
                }
            }

            vacs=new ArrayList<>(infoList);

        }
        if (vacs.size()>0){
             vacs=infoList;
            return getStockItem(queryMap, vacs);
        }



        return Collections.EMPTY_LIST;
    }

    private List<Map<String, Object>> getStockItem(Map<String, Object> queryMap, List<Map<String, Object>> vacs) {
        List<Map<String, Object>> inputList=vaccineChangeDao.queryInputList(queryMap);
        if (inputList.size()>0) {
            for (Map<String, Object> input : inputList) {
                for (Map<String, Object> info : vacs) {
                    if (input.get("baseId").equals(info.get("baseId"))){
                        info.put("inputAmount",input.get("amount"));
                        break;
                    }
                }
            }
        }
        //查询当月报损记录
        List<Map<String, Object>> damageList=vaccineChangeDao.queryDamageList(queryMap);
        if (damageList.size()>0){
            for (Map<String, Object> damage : damageList) {
                for (Map<String, Object> info : vacs) {
                    if (damage.get("infoId").equals(info.get("id"))){
                        info.put("damageAmount",damage.get("amount"));
                        break;
                    }
                }
            }
        }
        //查询当月退货记录
        List<Map<String, Object>> returnList=vaccineChangeDao.queryReturnList(queryMap);
        if (returnList.size()>0){
            for (Map<String, Object> returnMap : returnList) {
                for (Map<String, Object> info : vacs) {
                    if (returnMap.get("infoId").equals(info.get("id"))){
                        info.put("returnAmount",returnMap.get("amount"));
                        break;
                    }
                }
            }
        }
        //查询当月使用人份和报损人份
        List<Map<String, Object>> personAmount=vaccineChangeDao.queryUseAndDestroyList(queryMap);
        if (personAmount.size()>0) {
            for (Map<String, Object> person : personAmount) {
                for (Map<String, Object> info : vacs) {
                    if (person.get("baseId").equals(info.get("baseId"))){
                        info.put("usePersonAmount",person.get("useAmount"));
                        info.put("damagePersonAmount",person.get("destroyAmount"));
                        break;
                    }
                }
            }
        }
        //计算使用数量
        for (Map<String, Object> info : vacs) {
            //使用数量=使用人份数量/人份转换 取整数
            if (!StringUtils.isEmpty(info.get("type"))&&info.get("type").equals(0)&&info.get("usePersonAmount")!=null&&Long.valueOf(info.get("usePersonAmount").toString())>0) {
                Long usePersonAmount = Long.valueOf(info.get("usePersonAmount").toString());
                Long damagePersonAmount = Long.valueOf(info.get("damagePersonAmount").toString());
                Long conversion = Long.valueOf(info.get("conversion").toString());
                Long useAmont=usePersonAmount+damagePersonAmount;
                info.put("useAmount",useAmont%conversion==0?useAmont/conversion:useAmont/conversion+1L);
            }
        }
        //计算当月库存量
        //截止库存量=盘点数量+入库数量-报损数量-退货数量-（使用人份+报损人份）/人份转换
        //计算合计
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("productName","合计");
        totalMap.put("initAmount",0);
        totalMap.put("inputAmount",0);
        totalMap.put("damageAmount",0);
        totalMap.put("returnAmount",0);
        totalMap.put("useAmount",0);
        totalMap.put("damagePersonAmount",0);
        totalMap.put("usePersonAmount",0);
        totalMap.put("endAmount",0);
        if (vacs.size()>0) {
            for (Map<String, Object> info : vacs) {
                Long initAmount = Long.valueOf(info.get("initAmount").toString());
                Long inputAmount = Long.valueOf(info.get("inputAmount").toString());
                Long damageAmount = Long.valueOf(info.get("damageAmount").toString());
                Long returnAmount = Long.valueOf(info.get("returnAmount").toString());
                Long damagePersonAmount = Long.valueOf(info.get("damagePersonAmount").toString());
                Long usePersonAmount = Long.valueOf(info.get("usePersonAmount").toString());
                Long useAmount = Long.valueOf(info.get("useAmount").toString());

                Long endAmount= null;
                if (!StringUtils.isEmpty(info.get("type"))&&info.get("type").equals(0)){
                    Long conversion = Long.valueOf(info.get("conversion").toString());
                    endAmount=initAmount+inputAmount-damageAmount-returnAmount-(damagePersonAmount+usePersonAmount)/conversion;
                }else{
                    endAmount=initAmount+inputAmount-damageAmount-returnAmount-usePersonAmount-damagePersonAmount;
                }
                info.put("endAmount",endAmount);
                totalMap.put("endAmount",Long.valueOf(totalMap.get("endAmount").toString())+endAmount);
                totalMap.put("initAmount",Long.valueOf(totalMap.get("initAmount").toString())+initAmount);
                totalMap.put("inputAmount",Long.valueOf(totalMap.get("inputAmount").toString())+inputAmount);
                totalMap.put("damageAmount",Long.valueOf(totalMap.get("damageAmount").toString())+damageAmount);
                totalMap.put("returnAmount",Long.valueOf(totalMap.get("returnAmount").toString())+returnAmount);
                totalMap.put("damagePersonAmount",Long.valueOf(totalMap.get("damagePersonAmount").toString())+damagePersonAmount);
                totalMap.put("usePersonAmount",Long.valueOf(totalMap.get("usePersonAmount").toString())+usePersonAmount);
                totalMap.put("useAmount",Long.valueOf(totalMap.get("useAmount").toString())+useAmount);
            }
            vacs.add(totalMap);
        }

        return vacs;
    }

    @Override
    public List<Map<String, Object>> getCheckTimeList() {

        return vaccineChangeDao.getCheckTimeList();
    }
}
