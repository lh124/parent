package io.yfjz.service.bus.impl;

import com.alibaba.fastjson.JSON;
import io.yfjz.dao.basesetting.TBaseTowerDao;
import io.yfjz.dao.bus.ReceiveDao;
import io.yfjz.dao.mgr.*;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.mgr.TMgrStockOutItemEntity;
import io.yfjz.entity.mgr.TMgrStockOutTotalEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.bus.ReceiveService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * create by tianjinhai on 2018/8/11 14:58
 */
@Service
public class ReceiveServiceImpl implements ReceiveService {

    @Autowired
    private ReceiveDao receiveDao;
    @Autowired
    private TMgrStockInfoDao infoDao;
    @Autowired
    private TMgrStoreDao store;
    @Autowired
    private TBaseTowerDao towerDao;
    @Autowired
    private TMgrStockOutTotalDao totalDao;
    @Autowired
    private TMgrStockOutItemDao itemDao;


    @Override
    public List<Map<String, Object>> queryStockList(Map<String, Object> map) {
        //查询接种台绑定的疫苗编码
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //判断登录用户是不是村医
        if(userEntity.getRoleNames().contains(Constant.ROLE_COUNTRY)){

        }else{
            String towerId = userEntity.getInoculateTowerId();
            if (StringUtils.isEmpty(towerId)){
                return Collections.EMPTY_LIST;
            }
            //判断当前工作台是否是接种台
            TBaseTowerEntity tower = towerDao.queryObject(towerId);
            //如果登录的用户不为接种医生，不进行库存查询
            if (tower.getTowerNatureId()!=Constant.TOWER_TYPE_INOCULATE){
                return null;
            }
            //查询接种台绑定的疫苗ID集合
            List<String> vacList=receiveDao.queryBindVac(towerId);
            //如果集合不为空，查询绑定的疫苗，如果集合为空，查询所有库存疫苗
            if (vacList.size()>0){
                map.put("vacList",vacList);
            }
        }

        List<Map<String, Object>> list= receiveDao.queryStockList(map);
        return list;
    }

    @Override
    public int queryStockTotal(Map<String, Object> map) {
        //查询接种台绑定的疫苗编码
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //接种台ID
        if(userEntity.getRoleNames().contains(Constant.ROLE_COUNTRY)){

        }else{
            String towerId = userEntity.getInoculateTowerId();
            if (StringUtils.isEmpty(towerId)){
                return 0;
            }
        }
        return receiveDao.queryStockTotal(map);
    }

    @Override
    public int saveReceiveVac(Map param) throws Exception{
        Object rows = param.get("rows");
        List<Map<String,Object>> parse = (List<Map<String, Object>>) JSON.parse(rows.toString());
        //查询接种台绑定的仓库
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        TMgrStoreEntity towerStore =null;
        //如果登录的用户为村医
        if(userEntity.getRoleNames().contains(Constant.ROLE_COUNTRY)){
                towerStore=store.selectByUserId(userEntity.getUserId());
        }else{
            String towerId = userEntity.getInoculateTowerId();
            //判断当前工作台是否是接种台
            TBaseTowerEntity tower = towerDao.queryObject(towerId);
            //如果登录的用户不为接种医生，不能领取疫苗
            if (tower.getTowerNatureId() != Constant.TOWER_TYPE_INOCULATE) {
                throw new RRException("当前登录用户不是接种医生，不能领取疫苗");
            }
             towerStore = store.selectByTowerId(towerId);
            if (towerStore == null) {
                throw new RRException("接种台未绑定仓库，请绑定仓库之后再操作！");
            }
        }
        /**
         *插入出库总表记录
         */
        TMgrStockOutTotalEntity out = new TMgrStockOutTotalEntity();
        //出库时间
        out.setCreateTime(new Date());
        //创建人
        out.setCreateUserId(userEntity.getUserId());
        //出库人
        out.setOutStockUserId(userEntity.getUserId());
        //出库类型
        out.setOutType(Constant.OUT_TYPE_RECEIVE);
        //机构编码
        out.setOrgId(Constant.GLOBAL_ORG_ID);
        //出库单号 默认时间戳
        out.setStockOutCode(System.currentTimeMillis()+"");
        //出库备注
        out.setRemark("领取疫苗");
        //入库仓库ID
        out.setInStoreId(towerStore.getId());
        //出库ID
        String totalId = UUID.randomUUID().toString();
        out.setId(totalId);
        for (Map<String, Object> map : parse) {
            //设置出库的仓库
            if(out.getStoreId()==null){
                String storeId = map.get("storeId").toString();
                out.setStoreId(storeId);
                //更新出库总计记录
                totalDao.save(out);
            }
            //库存信息ID
            String id=map.get("id").toString();
            //领取的数量
            Long receiveNumber=Long.valueOf(map.get("receiveNumber").toString());
            //库存数量
            Long amount=Long.valueOf(map.get("amount").toString());
            //产品基础信息ID
            String baseId = map.get("baseId").toString();


            if(receiveNumber<0||receiveNumber>amount){
                throw  new RRException("领取数量必须大于0，小于等于库存数量");
            }
            //1、更新主仓库中的库存数量和人份数量
            TMgrStockInfoEntity baseInfo = infoDao.queryObject(id);
            //更新库存数量
            Long stockAmount=baseInfo.getAmount()-receiveNumber;
            baseInfo.setAmount(stockAmount);
            //更新人份数量
            Integer conversion = baseInfo.getConversion();
            //如果有人份转化，才更新人份数
            if(conversion!=null&&conversion>0){
                Long stockPersonAmount=baseInfo.getPersonAmount()-(receiveNumber*baseInfo.getConversion());
                baseInfo.setPersonAmount(stockPersonAmount);
            }
            //更新
            infoDao.update(baseInfo);
            /**
             * 新增或更新接种台仓库 库存
             */
            //查询接种台仓库中是否有该产品
            TMgrStockInfoEntity towerStockInfo = infoDao.queryByBaseInfoId(baseId, towerStore.getId(), null);
            if (towerStockInfo==null){
                towerStockInfo=new TMgrStockInfoEntity();
                //新增库存信息
                //剩余数量
                towerStockInfo.setAmount(receiveNumber);
                //领取数量
                towerStockInfo.setReceiveAmount(receiveNumber);
                //库存产品基本信息表ID
                towerStockInfo.setFkBaseInfo(baseId);
                //领取人
                towerStockInfo.setFkCreateUserId(userEntity.getUserId());
                //入库仓库ID
                towerStockInfo.setFkStoreId(towerStore.getId());
                //创建时间
                towerStockInfo.setCreateTime(new Date());
                //从哪一个设备出库的,还苗的时候还到对应的设备
                if(!StringUtils.isEmpty(baseInfo.getFkEquipmentId())){
                   towerStockInfo.setFkEquipmentId(baseInfo.getFkEquipmentId());
                }
                //人份总数
                if(conversion!=null&&conversion>0){
                    Long towerPersonAmount=receiveNumber*baseInfo.getConversion();
                    towerStockInfo.setPersonAmount(towerPersonAmount);

                }
                infoDao.save(towerStockInfo);

            }else{
                //更新库存信息
                Long totalAmount = towerStockInfo.getAmount()+receiveNumber;
                //剩余数量
                towerStockInfo.setAmount(totalAmount);
                //领取数量
                towerStockInfo.setReceiveAmount(towerStockInfo.getReceiveAmount()+receiveNumber);

                //人份总数
                if(conversion!=null&&conversion>0){
                    Long totalPersonAmount=towerStockInfo.getPersonAmount()+receiveNumber*baseInfo.getConversion();
                    towerStockInfo.setPersonAmount(totalPersonAmount);

                }
                //更新库存信息
                infoDao.update(towerStockInfo);
            }
            /**
             * 插入出库记录明细
             */
            TMgrStockOutItemEntity outItem = new TMgrStockOutItemEntity();
            //出库数量
            outItem.setAmount(receiveNumber);
            //创建时间
            outItem.setCreateTime(new Date());
            //库存信息ID
            outItem.setStockInfoId(id);
            //创建人
            outItem.setCreateUserId(userEntity.getUserId());
            //出库合计编号
            outItem.setTotalId(totalId);
            //出库设备
            if( map.get("equipId")!=null){
                outItem.setFkEquipmentId(map.get("equipId").toString());
            }

            //出库人份总数
            if(conversion!=null&&conversion>0){
                Long towerPersonAmount=receiveNumber*baseInfo.getConversion();
                outItem.setPersonAmount(towerPersonAmount);
            }
            //插入出库明细
            itemDao.save(outItem);
        }

        return 0;
    }

    @Override
    public List<Map<String, Object>> queryReceiveList(Map<String, Object> map) {
        //查询接种台绑定的仓库
        String storeId = getStoreId();
        if(storeId!=null) {
            map.put("storeId", storeId);
            List<Map<String, Object>> list = receiveDao.queryReceiveList(map);
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public int queryReceiveListTotal(Map<String, Object> map) {
        //查询接种台绑定的仓库
        String storeId = getStoreId();
        if(storeId!=null){
            map.put("storeId",storeId);
            int total = receiveDao.queryReceiveListTotal(map);
            return total;
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryReceiveTotalList(Map<String, Object> map) {
        String storeId = getStoreId();
        if(!StringUtils.isEmpty(storeId)){
            map.put("inStoreId",storeId);
            List<Map<String, Object>> total = totalDao.queryTotalList(map);
            return total;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public int queryReceiveTotalListTotal(Map<String, Object> map) {
        String storeId = getStoreId();
        if(storeId!=null){
            map.put("inStoreId",storeId);
           int total = totalDao.queryReceiveTotalListTotal(map);
            return total;
        }
        return 0;
    }
    private String getStoreId(){
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //如果登录的用户为村医
        TMgrStoreEntity towerStore = null;
        if (userEntity.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
            towerStore = store.selectByUserId(userEntity.getUserId());
        } else {
            String towerId = userEntity.getInoculateTowerId();
            if (StringUtils.isEmpty(towerId)) {
                return null;
            }
            TBaseTowerEntity tower = towerDao.queryObject(towerId);
            //如果登录的用户不为接种医生，不查询绑定仓库
            if (tower.getTowerNatureId() != Constant.TOWER_TYPE_INOCULATE) {
                return null;
            }
            towerStore = store.selectByTowerId(towerId);
        }
        return towerStore.getId();
    }


    @Override
    public List<Map<String, Object>> queryReceiveItemList(Map<String, Object> map) {
        List<Map<String, Object>> list= itemDao.queryReceiveItemList(map);
        return list;
    }

    @Override
    public int queryReceiveItemListTotal(Map<String, Object> map) {
        return itemDao.queryReceiveItemListTotal(map);
    }

    @Override
    public List<Map<String, Object>> queryBatchNum(Map<String, Object> map) {
        String storeId = getStoreId();
        if(!StringUtils.isEmpty(storeId)){
            map.put("inStoreId",storeId);
            List<Map<String, Object>> maps = receiveDao.queryBatchNum(map);
            return maps;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Map<String, Object>> queryReceiveListByExcel(Map<String, Object> temp) {
        //查询接种台绑定的仓库
        String storeId = getStoreId();
        if(storeId!=null) {
            temp.put("storeId", storeId);
            List<Map<String, Object>> list = receiveDao.queryReceiveListByExcel(temp);
            if (list!=null&&list.size()>0) {
                for (Map<String, Object> map : list) {
                    map.put("unit",map.get("dosenorm")+"/"+map.get("unitCode")+"/"+map.get("conversion")+"人份");
                }
            }
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Map<String, Object>> queryOtherBatchNum(Map<String, Object> map) {
        String storeId = getStoreId();
        if(!StringUtils.isEmpty(storeId)){
            map.put("inStoreId",storeId);
            List<Map<String, Object>> maps = receiveDao.queryOtherBatchNum(map);
            return maps;
        }
        return Collections.EMPTY_LIST;
    }
}
