package io.yfjz.service.child.impl;

import io.yfjz.dao.child.ChildInoculateDao;
import io.yfjz.service.child.ChildInoculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 儿童接种信息
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-12-22 17:54
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
@Service
public class ChildInoculateServiceImpl implements ChildInoculateService{

    @Autowired
    private ChildInoculateDao childInoculateDao;

    @Override
    public List<Map<String,Object>> viewInocChildInfoData(final Map<String,Object> map){
        return childInoculateDao.viewInocChildInfoData(map);
    }

    @Override
    public Integer viewInocChildInfoTotal(final Map<String,Object> map){
        return childInoculateDao.viewInocChildInfoTotal(map);
    }

    @Override
    public List<Map<String,Object>> listChildInoculateData(Map<String,Object> map){
        return childInoculateDao.listChildInoculateData(map);
    }
}