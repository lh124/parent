package io.yfjz.service.statics.impl;

import io.yfjz.dao.statistics.InoculateLosgDao;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.statics.InoculateService;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 刘琪
 * @class_name: InoculateServiceImpl
 * @Description: 接种日志统计报表
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-09-05 11:16
 */
@Service
public class InoculateServiceImpl implements InoculateService {

    @Autowired
    private InoculateLosgDao inoculateLosgDao;
    @Autowired
    private TChildInfoService tChildInfoService;

    @Override
    public R queryInoculateLogs(Map paramMap, String[] chilCommittees, String[] chilResidences, String[] infostatus, String[] biotypes, String[] bioNos, String[] schools,String[] inocDoctors,String[] inocbatchno) {
        //根据条件四个返回步骤：1：接种儿童数；2：按照疫苗接种属性统计；3:：按照疫苗剂次统计；4：按照行政村统计
        HashMap<String,Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayStart")))) {
            param.put("chilBirthdayStart", paramMap.get("chilBirthdayStart"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayEnd")))) {
            param.put("chilBirthdayEnd", paramMap.get("chilBirthdayEnd"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("inoculateStart")))) {
            param.put("inoculateStart", paramMap.get("inoculateStart"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("inoculateEnd")))) {
            param.put("inoculateEnd", paramMap.get("inoculateEnd"));
        }
        param.put("chilResidence", chilResidences);
        param.put("chilCommittees", chilCommittees);
        param.put("infostatus", infostatus);
        param.put("biotypes", biotypes);
        param.put("bioNos", bioNos);
        param.put("school", schools);
        param.put("inocDoctors", inocDoctors);
        param.put("inocbatchno", inocbatchno);
        param.put("org_id", ShiroUtils.getUserEntity().getOrgId());
        //需要将选中的疫苗和剂次拆分开，分别存入两个字段
        List<Map<String,Object>> historys = inoculateLosgDao.getInoulateLogs(param);
        Set set = new HashSet();
        for(Map<String,Object> map:historys){
            set.add(map.get("chil_code"));
        }
        int total = set.size();
        List<Map<String,Object>> inocpropertys = inoculateLosgDao.getInoulateLogsGroupInocProperty(param);
        List<Map<String,Object>> inocDoses = inoculateLosgDao.getInoulateLogsGroupDose(param);
        Map res = new HashMap();
        res.put("historys",historys);
        res.put("childcount",total);
        res.put("inocpropertys",inocpropertys);
        res.put("inocDoses",inocDoses);
        return R.ok(res);
    }


}