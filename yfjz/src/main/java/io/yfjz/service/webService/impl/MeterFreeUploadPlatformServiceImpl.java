package io.yfjz.service.webService.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.yfjz.dao.basesetting.TBaseCommitteeDao;
import io.yfjz.dao.basesetting.TBaseSchoolDao;
import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import io.yfjz.entity.basesetting.TBaseSchoolEntity;
import io.yfjz.service.webService.MeterFreeUploadPlatformService;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.ShiroUtils;
import io.yfjz.utils.ZipUtils;
import io.yfjz.utils.encrypt.DESUtils;
import io.yfjz.webservice.UploadPlatformWebservice;
import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 上传来地址的行政村和学校到平台上去
 * @Date 12:03 2018/11/15
 */
@Service("meterFreeUploadPlatformService")
public class MeterFreeUploadPlatformServiceImpl implements MeterFreeUploadPlatformService {
    private static Logger log = Logger.getLogger(MeterFreeUploadPlatformServiceImpl.class);
    @Resource
    private UploadPlatformWebservice uploadPlatformWebservice;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private TBaseCommitteeDao tBaseCommitteeDao;

    @Resource
    private TBaseSchoolDao tBaseSchoolDao;

    @Override
    public Map<String, Object> updatePlatform() {
        Map<String,Object> mapUploading = new HashMap<>();
        Map<String, Object> map = new HashMap<>(3);
        try {
            //获取机构编号
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            String committeeSql = "select code,name,pinyin_initials,iscount,org_id,create_time,delete_status from t_base_committee";
            String schoolSql = "select code,name,sort_name,org_id,address,phone,link_man,create_time,delete_status from t_base_school";
            //使用jdbcTemplate查询行政村和学校
            List<Map<String,Object>> committee = jdbcTemplate.queryForList(committeeSql);
            List<Map<String,Object>> school = jdbcTemplate.queryForList(schoolSql);
            /*Map<String,Object> where = new HashMap<String,Object>(){{
                this.put("org_id",orgId);
                this.put("offset",null);
                this.put("limit",null);
            }};
            List<TBaseCommitteeEntity> committee = tBaseCommitteeDao.queryList(where);
            List<TBaseSchoolEntity> school = tBaseSchoolDao.queryList(where);*/
            mapUploading.put("t_base_committee",committee);
            mapUploading.put("t_base_school",school);
           log.info("上传数据为："+mapUploading.toString());
            //加密机构编码和上传数据
            String encrypt = StringToBytes(JSON.toJSONString(mapUploading, SerializerFeature.WriteMapNullValue));
            Map<String,String> verif = verification(orgId);

            String result = uploadPlatformWebservice.uploadingPlatformData(verif.get("orgId"),verif.get("password"),encrypt);

            log.info("上传平台返回值："+result);
            JSONObject json = JSONObject.parseObject(result);
            //判断平台保存数据是否成功
            if("0".equals(json.getString("code"))){
                map.put("code",0);
                map.put("msg","上传行政村和学校数据成功！");
            }else{
                map.put("code",1001);
                map.put("msg","平台更新数据失败！");
            }

        }catch(Exception e){
            log.error(this,e);
            map.put("code",1000);
            map.put("msg","上传行政村和学校数据出错！");
        }
       return map;
    }

    /**
     * 将字符串加密处理
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String StringToBytes(String content) throws Exception, GeneralSecurityException {
        if (StringUtils.isEmpty(content)) return "";
        String k = PropertiesUtils.getMapValue("key");
        byte[] key = new BASE64Decoder().decodeBuffer(k);//加密密文
        byte[] zip = ZipUtils.zip(content.getBytes("UTF-8"));//压缩
        byte[] encrypt = DESUtils.des3EncodeECB(key,zip);//加密
        return new BASE64Encoder().encode(encrypt);//转String类型
    }

    public static Map<String,String> verification(String orgId) throws Exception, GeneralSecurityException {
        long time = new Date().getTime();
        byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("key")+time);//加密密文
        byte[] orgIdECB = DESUtils.des3EncodeECB(key,orgId.getBytes("UTF-8"));
        String mm = new BASE64Encoder().encode(orgIdECB)+"【精英】"+time;
        byte[] passwordECB = DESUtils.des3EncodeECB(key,mm.getBytes("UTF-8"));
        //String orgId = new BASE64Encoder().encode(orgIdECB);
        //String password = new BASE64Encoder().encode(passwordECB);
        return new HashMap<String,String>(){{
            this.put("orgId",new BASE64Encoder().encode(orgIdECB));
            this.put("password",new BASE64Encoder().encode(passwordECB));
        }};
    }
}
