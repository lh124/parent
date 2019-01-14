package io.yfjz.service.report.impl;

import io.yfjz.dao.mgr.TMgrStockInItemDao;
import io.yfjz.service.report.VaccineUseStatisticsService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

/**
 * create by tianjinhai on 2018/9/10 14:13
 */
@Service
public class VaccineUseStatisticsServiceImpl implements VaccineUseStatisticsService {

    @Autowired
    private TMgrStockInItemDao itemDao;
    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {
        List<Map<String, Object>> list=itemDao.queryReceiveVaccineRecord(queryMap);
        if (list.size()>0){
            //查询被其他接种台使用的疫苗
            List<Map<String, Object>> useList=itemDao.queryOtherUseVaccine(queryMap);
            if (useList.size()>0){
                for (Map<String, Object> useMap : useList) {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("id",useMap.get("id"));
                    temp.put("username",useMap.get("username"));
                    temp.put("towerName",useMap.get("towerName"));
                    temp.put("productName",useMap.get("productName"));
                    temp.put("fk_vaccine_code",useMap.get("vacCode"));
                    temp.put("conversion",useMap.get("conversion"));
                    temp.put("batchnum",useMap.get("batchnum"));
                    temp.put("towerId",useMap.get("receiveTowerId"));
                    temp.put("expiryDate",useMap.get("expiryDate"));
                    temp.put("usePersonAmount",useMap.get("useAmount"));
                    temp.put("belong",useMap.get("belong"));
                    list.add(temp);
                    for (Map<String, Object> map : list) {
                        Object receiveTowerId = useMap.get("receiveTowerId");
                        Object useBase = useMap.get("baseId");
                        Object towerId = map.get("towerId");
                        Object baseId = map.get("baseId");
                        if(receiveTowerId.equals(towerId)&&useBase.equals(baseId)&&!useMap.get("id").equals(map.get("id"))){
                            map.put("usePersonAmount",Integer.valueOf(map.get("usePersonAmount").toString())-Integer.valueOf(useMap.get("useAmount").toString()));
                            break;
                        }
                    }

                }
            }
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("towerName","合计");
            tempMap.put("receivePersonAmount",0);
            tempMap.put("usePersonAmount",0);
            tempMap.put("damagePersonAmount",0);
            tempMap.put("remainAmount",0);
            tempMap.put("returnAmount",0);
            for (Map<String, Object> map : list) {
                Long receivePersonAmount =0L;
                if (map.get("receivePersonAmount")!=null){
                     receivePersonAmount = Long.valueOf(map.get("receivePersonAmount").toString());
                }
                Long usePersonAmount =0L;
                if (map.get("usePersonAmount")!=null){
                    usePersonAmount = Long.valueOf(map.get("usePersonAmount").toString());
                }
                Long damagePersonAmount =0L;
                if (map.get("damagePersonAmount")!=null){
                    damagePersonAmount = Long.valueOf(map.get("damagePersonAmount").toString());
                }
                Long remainAmount =0L;
                if (map.get("receivePersonAmount")!=null){
                    remainAmount = Long.valueOf(map.get("remainAmount").toString());
                }
                Long returnAmount =0L;
                if(map.get("returnAmount")!=null){
                    returnAmount = Long.valueOf(map.get("returnAmount").toString());
                }

                tempMap.put("receivePersonAmount",Long.valueOf(tempMap.get("receivePersonAmount").toString())+receivePersonAmount);
                tempMap.put("usePersonAmount",Long.valueOf(tempMap.get("usePersonAmount").toString())+usePersonAmount);
                tempMap.put("damagePersonAmount",Long.valueOf(tempMap.get("damagePersonAmount").toString())+damagePersonAmount);
                tempMap.put("remainAmount",Long.valueOf(tempMap.get("remainAmount").toString())+remainAmount);
                tempMap.put("returnAmount",Long.valueOf(tempMap.get("returnAmount").toString())+returnAmount);
            }
            list.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1= MapUtils.getString(o1, "towerName");
                    String name2=MapUtils.getString(o2, "towerName");
                    Collator instance = Collator.getInstance(Locale.CHINA);
                    return instance.compare(name1, name2);

                }
            });
            list.add(tempMap);
            return list;
        }
        return Collections.EMPTY_LIST;
    }
}
