package io.yfjz.service.bus.impl;

import com.alibaba.fastjson.JSON;
import io.yfjz.dao.basesetting.TBaseTowerDao;
import io.yfjz.dao.bus.ReturnDao;
import io.yfjz.dao.mgr.TMgrStockInItemDao;
import io.yfjz.dao.mgr.TMgrStockInTotalDao;
import io.yfjz.dao.mgr.TMgrStockInfoDao;
import io.yfjz.dao.mgr.TMgrStoreDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.mgr.TMgrStockInItemEntity;
import io.yfjz.entity.mgr.TMgrStockInTotalEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.bus.ReturnService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.REException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by tianjinhai on 2018/8/15 15:48
 */
@Service
public class ReturnServiceImpl implements ReturnService {
    @Autowired
    private TMgrStockInfoDao infoDao;
    @Autowired
    private TMgrStoreDao store;
    @Autowired
    private TBaseTowerDao towerDao;
    @Autowired
    private ReturnDao returnDao;
    @Autowired
    private TMgrStockInTotalDao totalDao;
    @Autowired
    private TMgrStockInItemDao itemDao;

    @Override
    public List<Map<String, Object>> queryStockList(Map<String, Object> map) {
        String storeId = getStoreId();
        if (!StringUtils.isEmpty(storeId)) {
            map.put("storeId",storeId);
            List<Map<String, Object>> list= returnDao.queryStockList(map);
            //计算归还数量和报损人份的默认值
            //归还数量=剩余数量-(使用人份%人份转换==0?使用人份/人份转换：使用人份/人份转换+1);
            if (list.size()>0) {
                for (Map<String, Object> tempMap: list) {
                    //剩余数量
                    Long amount = Long.valueOf(tempMap.get("amount").toString());
                    //使用人份
                    Long useAmount = Long.valueOf(tempMap.get("useAmount").toString());
                    //人份转换
                    Long convision = Long.valueOf(tempMap.get("conversion").toString());
                    //判断使用人份为0 或者人份转换为1，则默认报损数量为0，剩余数量为归还数量
                        tempMap.put("returnAmount",amount);
                    if(useAmount==0||convision==1||useAmount%convision==0){
                        tempMap.put("destroyAmount","0");
                    }else if (useAmount<convision && convision>1){
                        tempMap.put("destroyAmount",convision-useAmount);
                    }else if(useAmount>convision && convision>1){
                        Long destroyAmount=convision-useAmount%convision;
                        tempMap.put("destroyAmount",destroyAmount);
                    }
                }
            }
            //报损人份

            return list;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public int queryStockTotal(Map<String, Object> map) {
        String storeId = getStoreId();
        if (!StringUtils.isEmpty(storeId)) {
            map.put("storeId",storeId);
            int total= returnDao.queryStockTotal(map);
            return total;
        }
        return 0;
    }

    private String getStoreId(){
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        TMgrStoreEntity towerStore = null;
        //如果是村医 查询村医的仓库
        if (userEntity.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
            towerStore = store.selectByUserId(userEntity.getUserId());
        } else {
            String towerId = userEntity.getInoculateTowerId();
            if (StringUtils.isEmpty(towerId)){
                return  null;
            }
            TBaseTowerEntity tower = towerDao.queryObject(towerId);
            //如果登录的用户不为接种医生，不查询绑定仓库
            if (tower.getTowerNatureId()==null||tower.getTowerNatureId()!= Constant.TOWER_TYPE_INOCULATE){
                return null;
            }
             towerStore=store.selectByTowerId(towerId);
            if(towerStore==null){
                throw new RRException("接种台未绑定仓库，请绑定仓库之后再操作！");
            }
        }
        return  towerStore.getId();
    }

    @Override
    public int saveReturnVac(Map map) throws ParseException {
        String rows = map.get("rows").toString();
        if (!StringUtils.isEmpty(rows)){
            //解析数据
            List<Map<String,Object>> rowList = JSON.parseObject(rows, List.class);
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //为主仓库添加入库记录，入库类型疫苗归还，删除接种台的库存记录(伪删除)
            //入库单号
            String order = System.currentTimeMillis()+"";
            //入库备注
            String remark = "归还疫苗";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //入库仓库
            String store = rowList.get(0).get("returnStore").toString();
            //插入总的入库记录
            TMgrStockInTotalEntity total = new TMgrStockInTotalEntity();
            String id = UUID.randomUUID().toString();
            //Id
            total.setId(id);
            //创建时间
            total.setCreateTime(new Date());
            //创建人
            total.setFkCreateUserId(userEntity.getUserId());
            //入库类型
            total.setInType(Constant.IN_TYPE_RETURN);
            //机构编码
            total.setOrgId(Constant.GLOBAL_ORG_ID);
            //入库备注
            total.setRemark(remark);
            //入库单号
            total.setStockInCode(order);
            //入库人
            total.setFkInStockUser( userEntity.getUserId());
            TMgrStoreEntity storeEntity = new TMgrStoreEntity();
            storeEntity.setId(store);
            //仓库ID
            total.setStore(storeEntity);
            //入库时间
            total.setStorageTime(new Date());
            totalDao.save(total);
            //插入库存明细
            if (rowList.size()>0){
                for (Map<String, Object> row : rowList) {
                    TMgrStockInItemEntity item = new TMgrStockInItemEntity();
                    //归还数量
                    Long amount = Long.valueOf(row.get("returnAmount").toString());
                    //报损人份
                    Long destoryAmount = Long.valueOf(row.get("destroyAmount").toString());
                    item.setDestoryAmount(destoryAmount);
                    //产品基本信息ID
                    String baseInfoId = row.get("baseId").toString();
                    //入库数量
                    item.setAmount(amount);
                    //库存产品基本信息表ID
                    item.setFkStockInfoId(baseInfoId);
                    Long personAmount=null;
                    if (row.get("conversion")!=null){
                        Long conversion= Long.valueOf(row.get("conversion").toString());
                        //人份总数=入库数量*转化人份数
                        personAmount=amount*conversion;
                        item.setPersonAmount(personAmount);
                    }
                   /* String equipmentId=null;
                    if(row.get("equipmentName")!=null){
                        equipmentId=row.get("equipmentName").toString();
                        item.setFkEquipmentId(equipmentId);
                    }*/
                    item.setCreateTime(new Date());
                    //汇总记录ID
                    item.setTotalId(total.getId());
                    //设置还苗的工作台
                    item.setTowerId(userEntity.getInoculateTowerId());
                    //领取数量
                    item.setReceiveAmount(Long.valueOf(row.get("receiveAmount").toString()));
                    //使用人份
                    item.setUseAmount(Long.valueOf(row.get("useAmount").toString()));
                    //插入库存明细
                    itemDao.save(item);
                    /**
                     * 库存中可能存在多条记录，因为一个产品可以放在多个设备中，默认放到第一个设备
                     */
                    List<TMgrStockInfoEntity> base =infoDao.queryStoreInfo(baseInfoId,store);

                    if (base.size()<1){
                        TMgrStockInfoEntity baseInfo =new TMgrStockInfoEntity();
                        //新增库存信息
                        //归还数量
                        baseInfo.setAmount(amount);
                        //库存产品基本信息表ID
                        baseInfo.setFkBaseInfo(baseInfoId);
                        //创建人ID
                        baseInfo.setFkCreateUserId(userEntity.getUserId());
                        //入库仓库ID
                        baseInfo.setFkStoreId(store);
                        //创建时间
                        baseInfo.setCreateTime(new Date());
                        if (personAmount!=null){
                            //人份总数
                            baseInfo.setPersonAmount(personAmount);
                        }
                        infoDao.save(baseInfo);
                    }else{
                        //更新库存信息,只需要更新库存数量，人份总数量,默认更新第一个设备中的数据
                        //原数量
                        TMgrStockInfoEntity info = base.get(0);
                        Long baseAmount = info.getAmount();
                        Long totalAmount=baseAmount+amount;
                        //总数量
                        info.setAmount(totalAmount);
                        //总人份数
                        if (personAmount!=null){
                            //人份总数
                            info.setPersonAmount(info.getPersonAmount()+personAmount);
                        }
                        infoDao.update(info);
                    }
                    //将接种台仓库的剩余库存,人份数量，使用人份，领取人份设置为0
                    String infoId = row.get("infoId").toString();
                    TMgrStockInfoEntity towerInfo = infoDao.queryObject(infoId);
                    towerInfo.setAmount(0L);
                    towerInfo.setPersonAmount(0L);
                    towerInfo.setReceiveAmount(0L);
                    towerInfo.setUseAmount(0L);
                    infoDao.updateOther(towerInfo);
                }

             }
             //表示操作成功
             return  0;
        }
        //表示操作失败
        return 1;
    }



    @Override
    public List<Map<String, Object>> queryReturnTotalList(Map<String, Object> map) {
        String storeId = getStoreId();
        if (!StringUtils.isEmpty(storeId)){
            map.put("storeId",storeId);
            map.put("type",Constant.IN_TYPE_RETURN);
            List<Map<String, Object>> tMgrStockInTotalEntities = totalDao.queryListMap(map);
            return tMgrStockInTotalEntities;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public int queryReturnTotal(Map<String, Object> map) {
        String storeId = getStoreId();
        if (!StringUtils.isEmpty(storeId)){
            map.put("storeId",storeId);
            map.put("type",Constant.IN_TYPE_RETURN);
            int total= totalDao.queryTotal(map);
            return total;
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryReturnItemList(Map<String, Object> map) {

            List<Map<String, Object>>  items = itemDao.queryItemListMap(map);
            if (items.size()>0){
                return items;
            }
            return Collections.EMPTY_LIST;
    }

    @Override
    public int queryReturnItemTotal(Map<String, Object> map) {

        return itemDao.queryTotal();
    }

    @Override
    public void returnPersonAmountByZero(TMgrStockInfoEntity infoEntity) throws ParseException {
        Map<String, Object> temp = new HashMap<>();
        temp.put("conversion",infoEntity.getConversion());
        temp.put("destroyAmount",0);
        temp.put("returnAmount",0);
        temp.put("baseId",infoEntity.getFkBaseInfo());
        temp.put("receiveAmount",infoEntity.getReceiveAmount());
        temp.put("useAmount",infoEntity.getUseAmount());
        temp.put("infoId",infoEntity.getId());
        //查询仓库Id
        List<Map> storeList=towerDao.queryStoreIdByCode(infoEntity.getFkBaseInfo());
        temp.put("returnStore", storeList.get(0).get("storeId"));
        Map transMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(temp);
        transMap.put("rows",JSON.toJSONString(list));
        saveReturnVac(transMap);

    }



}
