package io.yfjz.service.report.impl;

import com.ctc.wstx.util.DataUtil;
import io.yfjz.dao.statistics.DynamicChildDao;
import io.yfjz.service.report.DynamicChildService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * create by tianjinhai on 2018/11/1 12:33
 */
@Service
public class DynamicChildServiceImpl implements DynamicChildService {

    @Autowired
    private DynamicChildDao dynamicChildDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {
       /* String firstDay = DateUtils.getFirstDayOfMonth(Integer.valueOf(queryMap.get("year").toString()), Integer.valueOf(queryMap.get("month").toString()));
        String lastDay = DateUtils.getLastDayOfMonth(Integer.valueOf(queryMap.get("year").toString()), Integer.valueOf(queryMap.get("month").toString()));*/
        queryMap.put("firstDay",queryMap.get("year"));
        queryMap.put("lastDay",queryMap.get("month"));
        queryMap.put("orgId", Constant.GLOBAL_ORG_ID);
        /*if(queryMap.get("chilCommittees")==null||"".equals(queryMap.get("chilCommittees")) || "null".equals(queryMap.get("chilCommittees"))){
            queryMap.put("chilCommittees",null);
        }else{
            String str2=queryMap.get("chilCommittees").toString();
            String chilCommittees[]= str2.split(",");
            queryMap.put("chilCommittees", chilCommittees);
        }*/
        List<Map<String, Object>> list= dynamicChildDao.queryList(queryMap);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map<String, Object> map : list) {
            Object hopital = map.get("chil_birthhospital");
            Object inotime = map.get("inotime");
            Object chil_birthday = map.get("chil_birthday");

           if (StringUtils.isEmpty(hopital)||"0000000000".equals(hopital)){
               map.put("xianji","√");
           }else{
               map.put("xianji",hopital);
           }
           if (StringUtils.isEmpty(inotime)){
               map.put("inotime","否");
           }else{
               map.put("inotime",sdf.format(inotime));
           }
            if (!StringUtils.isEmpty(chil_birthday)){
                map.put("chil_birthday",sdf.format(chil_birthday));
            }
        }
        return list;
    }
}
