package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrStockInItemDao;
import io.yfjz.dao.mgr.TMgrStockInTotalDao;
import io.yfjz.entity.mgr.*;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.mgr.TMgrStockInTotalService;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.utils.ShiroUtils;
import org.codehaus.groovy.syntax.Numbers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by tianjinhai on 2018/8/7 9:41
 */
@Service
public class TMgrStockInTotalServiceImpl implements TMgrStockInTotalService {

    @Autowired
    private TMgrStockInTotalDao totalDao;
    @Autowired
    private TMgrStockInItemDao itemDao;
    @Autowired
    private TMgrStockInfoService infoService;

    @Override
    public List<TMgrStockInTotalEntity> queryList(Map<String, Object> map) {
        List<TMgrStockInTotalEntity> tMgrStockInTotalEntities = totalDao.queryList(map);
        return tMgrStockInTotalEntities;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        int total = totalDao.queryTotal(map);
        return total;
    }

    @Override
    public List<Map<String,Object>> queryItemList(Map<String, Object> map) {
        List<Map<String,Object>>  items = itemDao.queryItemList(map);
        return items;
    }

    @Override
    public int queryItemTotal(Map<String, Object> map) {
        int total = itemDao.queryTotal(map);
        return total;
    }

    @Override
    public TMgrStockInTotalEntity queryInfoById(String id) {
        return totalDao.queryObject(id);
    }

    @Override
    public void updateAmount(Map map) {
        TMgrStockInItemEntity entity = itemDao.queryObject(map.get("id"));
        ModifyStock modify = new ModifyStock();
        modify.setBaseId(map.get("baseId").toString());
        modify.setItemId(map.get("id").toString());
        modify.setCreateTime(new Date());
//        modify.setRemark(map.get("remark").toString());
        modify.setPreAmount(entity.getAmount());
        Long modifyAmount = Long.valueOf(map.get("amount").toString());
        //计算差异数量
        long result = entity.getAmount() - modifyAmount;
        if (map.get("conversion")!=null&&!"".equals(map.get("conversion"))){
            Long conversion = Long.valueOf(map.get("conversion").toString());
            Long towerPersonAmount=modifyAmount*conversion;
            entity.setPersonAmount(towerPersonAmount);
        }

        modify.setNowAmount(modifyAmount);
        entity.setAmount(modifyAmount);
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        modify.setCreateUser(userEntity.getRealName());
        itemDao.insertModify(modify);
        itemDao.update(entity);
        //修改库存数量
        TMgrStockInfoEntity info=infoService.queryStockInfoByEquipmentAndStore(map);
        if (result>0){
            long infoRet = info.getAmount() - result;
            info.setAmount(infoRet);
            if (map.get("conversion")!=null&&!"".equals(map.get("conversion"))){
                Long conversion = Long.valueOf(map.get("conversion").toString());
                Long personAmount=infoRet*conversion;
                info.setPersonAmount(personAmount);
            }
        }else{
            long infoRet = info.getAmount()+ Math.abs(result);
            info.setAmount(infoRet);
            if (map.get("conversion")!=null&&!"".equals(map.get("conversion"))){
                Long conversion = Long.valueOf(map.get("conversion").toString());
                Long personAmount=infoRet*conversion;
                info.setPersonAmount(personAmount);
            }
        }
        infoService.update(info);


    }

    @Override
    public List<Map<String,Object>> getModifyList(Map<String, Object> map) {

        return itemDao.getModifyList(map);

    }

    @Override
    public int getModifyListTotal(Map<String, Object> map) {
        return itemDao.getModifyListTotal(map);
    }
}
