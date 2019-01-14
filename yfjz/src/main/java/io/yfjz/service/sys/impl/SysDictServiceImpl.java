//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;

import com.alibaba.fastjson.JSON;
import io.yfjz.dao.sys.SysDictDao;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.service.sys.SysDictService;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictDao sysDictDao;

    public SysDictServiceImpl() {
    }

    public void save(SysDictEntity config) {
        this.sysDictDao.save(config);
    }

    public void update(SysDictEntity config) {
        this.sysDictDao.update(config);
    }

    public void updateValueByKey(String key, String value) {
        this.sysDictDao.updateValueByKey(key, value);
    }

    public void deleteBatch(String[] ids) {
        this.sysDictDao.deleteBatch(ids);
    }

    public List<SysDictEntity> queryList(Map<String, Object> map) {
        return this.sysDictDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.sysDictDao.queryTotal(map);
    }

    public SysDictEntity queryObject(String id) {
        return this.sysDictDao.queryObject(id);
    }

    public String getValue(String key, String defaultValue) {
        String value = this.sysDictDao.queryByKey(key);
        return StringUtils.isBlank(value)?defaultValue:value;
    }

    public <T> T getConfigObject(String key, Class<T> clazz) throws Exception {
        String value = this.getValue(key, null);
        return StringUtils.isNotBlank(value)?JSON.parseObject(value, clazz):clazz.newInstance();
    }

    @Override
    public List selectListByType(String type) {
        List<SysDictEntity> list= sysDictDao.selectListByType(type);
        return list;
    }

    @Override
    public void startUsingBatch(String[] ids) {
        sysDictDao.startUsingBatch(ids);
    }

    @Override
    public void forbiddenBatch(String[] ids) {
        sysDictDao.forbiddenBatch(ids);
    }

    @Override
    public R queryMapList() {
        //code_inoculation_site  code_vaccine_property
        try {
            List list = new ArrayList<>();
            //接种属性
            List<SysDictEntity> codeVaccineProperty = sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", "code_vaccine_property");
            }});
            //接种部位
            List<SysDictEntity> codeInoculationSite = sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", "code_inoculation_site");
            }});

            list.add(codeInoculationSite);
            list.add(codeVaccineProperty);
            return R.ok().put("list",list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系統异常{}"+e.getMessage());
        }
    }

    @Override
    public R queryResultTable(String ttype) {

        List<SysDictEntity> codeVaccineProperty = new ArrayList<>();
        if(StringUtils.isNotBlank(ttype)){
            codeVaccineProperty = sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", ttype);
            }});
            return R.ok().put("dict",codeVaccineProperty);
        }else{
            Map<String,Object> map = new HashMap<>();
            //加载个案状态
            map.put("child_info_status",sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", "child_info_status");
            }}));
            //加载居住属性
            map.put("child_residence_code",sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", "child_residence_code");
            }}));
            //加载户籍属性
            map.put("movetype_code",sysDictDao.queryMapList(new HashMap<String, Object>() {{
                put("ttype", "movetype_code");
            }}));
            return R.ok().put("data",map);
        }
    }
}
