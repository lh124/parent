package io.yfjz.service.sys.impl;


import io.yfjz.dao.sys.SysConfigurationDao;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysConfigurationService;
import io.yfjz.utils.CommonUtils;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 系统相关功能是否启用配置
 * @Date 17:00 2018/10/19
 */
@Service("sysConfigurationService")
public class SysConfigurationServiceImpl implements SysConfigurationService {

    @Autowired
    private SysConfigurationDao sysConfigurationDao;

    @Override
    public SysConfigurationEntity configuration(String type) {
        SysConfigurationEntity config = sysConfigurationDao.queryObject(type);
        //判断查询出来值为空，添加进去
        if (config == null || config .equals("null") ) {
            config = new SysConfigurationEntity();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
            if(sysUserEntity == null){
                config.setCreator(null);
            }else{
                config.setCreator(sysUserEntity.getUserId());
            }
            config.setId(uuid);
            config.setCreateTime(new Date());
            config.setType(type);
            config.setStartUsing(1);
            //添加没有的数据
            sysConfigurationDao.save(config);
        }
        return config;
    }

    @Override
    public void offernumberStatus(SysConfigurationEntity entity) {
        SysConfigurationEntity config = sysConfigurationDao.queryObject(entity.getType());
        //判断是否为空如果为空则添加这条数据
        if (config == null) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
            entity.setId(uuid);
            entity.setCreateTime(new Date());
            entity.setCreator(sysUserEntity.getUserId());
            //添加没有的数据
            sysConfigurationDao.save(entity);
        } else {

            sysConfigurationDao.update(entity);
        }
    }
}
