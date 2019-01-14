package io.yfjz.service.bus.impl;

import com.alibaba.fastjson.JSON;
import io.yfjz.dao.bus.TBusBatchInoculationDao;
import io.yfjz.dao.bus.TBusRegisterDao;
import io.yfjz.dao.child.TChildInfoDao;
import io.yfjz.dao.child.TChildInoculateDao;
import io.yfjz.dao.child.TChildMoveDao;
import io.yfjz.dao.mgr.TMgrStockInfoDao;
import io.yfjz.dao.mgr.TMgrStoreDao;
import io.yfjz.dao.sys.SysDictDao;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.child.TChildMoveEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.service.bus.BatchInoculateService;
import io.yfjz.service.bus.ReturnService;
import io.yfjz.utils.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 批量补录
 * @author 田金海
 * @date 2018/09/04
 */
@Service
public class BatchInoculateServiceImpl implements BatchInoculateService {

    @Autowired
    private TBusBatchInoculationDao busBatchInoculationDao;
    @Autowired
    InitPlanService initPlanService;

    @Autowired
    TBusRegisterDao tBusRegisterDao;

    @Autowired
    private TMgrStoreDao store;
    @Autowired
    private TMgrStockInfoDao infoDao;
    @Autowired
    private TChildInoculateDao tChildInoculateDao;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private SysDictDao sysDictDao;
    @Autowired
    private TChildInfoDao childInfoDao;
    @Autowired
    private TChildMoveDao tChildMoveDao;



    @Override
    public void saveQueryResult(List<ChildData> queryResult, Map<String, Object> param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String times = sdf.format(new Date());
        for (ChildData childData : queryResult) {
            childData.setOrgId(Constant.GLOBAL_ORG_ID);
            childData.setId(UUID.randomUUID().toString());
            childData.setSelectCondition(JSON.toJSONString(param));

            childData.setSelectTimes(times);
        }
      busBatchInoculationDao.saveBatchResult(queryResult);

    }

    @Override
    public int queryTotal(Query query) {
        return  busBatchInoculationDao.queryTotal(query);
    }

    @Override
    public List<ChildData> queryList(Query query) {
        return busBatchInoculationDao.queryListByCondition(query);
    }

    @Override
    public List<Map<String, Object>> queryAllTimes() {
        List<Map<String, Object>>  list=busBatchInoculationDao.queryAllTimes();
        return list;
    }

    @Override
    public int deleteTimes(String selectTimes) {
        return busBatchInoculationDao.deleteTimes(selectTimes);
    }

    @Override
    public Map saveInoculateInfo(List<Map<String,Object>> list) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前选择的接种台仓库ID
        Map<String, Object> ret = new HashMap<>();
        SysUserEntity user = ShiroUtils.getUserEntity();
        TMgrStoreEntity towerStore = null;
        if (user.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
            towerStore = store.selectByUserId(user.getUserId());
        } else {
            String towerId = user.getInoculateTowerId();
            towerStore=store.selectByTowerId(towerId);
            if (towerStore==null){
                throw new RRException("该用户无权进行录入操作，请切换用户！");
            }
        }
        //计算需要多少疫苗,查询库存是否充足
        List<Map<String, Object>> totalVaccines = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        for (Map<String, Object> map : list) {
            //判断补录的类型,web网页补录，excel，表格补录
            if (map.get("isInoc")==null||StringUtils.isEmpty(map.get("isInoc"))) {
                ret.put("type","web");
            }else{
                ret.put("type","excel");
            }
            String names = map.get("planName").toString();
            String classCode = busBatchInoculationDao.queryVaccineClass(names);
            Object code = temp.get(classCode);
            if (code == null) {
                temp.put(classCode, 1);
            } else {
                temp.put(classCode, Integer.valueOf(code.toString()) + 1);
            }

        }
        //查询库存是否足够
        Set<Map.Entry<String, Object>> entries = temp.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String classId = entry.getKey();
            Integer number = (Integer) entry.getValue();
            Map<String, Object> receiveMap = infoDao.queryStockAmount(classId, towerStore.getId());
            if (receiveMap==null||Integer.valueOf(receiveMap.get("number").toString()) < number) {
                throw new RRException("领取的疫苗不足，请检查领取数量！");
            }
        }
        for (Map<String, Object> m : list) {
            //判断儿童该剂次是否已经接种

            //根据疫苗大类名称，查询库存中是否有该疫苗
            String planName = m.get("planName").toString().trim();
            String className = busBatchInoculationDao.queryVaccineClass(planName);
            //根据疫苗分类ID和仓库ID查询出所有批号的疫苗，按疫苗是否收费，疫苗失效期扣减库存，优先扣减免费，失效期短的库存
            List<Map<String, Object>> vaccines =null;
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("classCode",className);
            queryMap.put("storeId",towerStore.getId());
            queryMap.put("batchnum",m.get("batchnum"));
            if("web".equals(ret.get("type"))){
              vaccines = infoDao.queryReceiveVaccine(className, towerStore.getId());
            }else{
                vaccines=infoDao.queryReceiveVaccineByBatchnum(queryMap);
                if(vaccines.size()<=0){
                    throw new RRException("批号为("+m.get("batchnum")+")疫苗领取不足或批号错误，请领取后操作！");
                }
            }
           /* if (vaccines.size() <= 0) {
                throw new RRException("没有领取疫苗或库存不足，请领取疫苗之后再继续操作！");
            }*/
            Map<String, Object> vaccine = vaccines.get(0);
            TChildInoculateEntity tChildInoculateEntity = null;
            m.put("vaccineCode",vaccine.get("vaccineCode"));
            int vacccodeNum=busBatchInoculationDao.queryInoculateVacccodeNumByMap(m);
            if (vacccodeNum>0) {
                throw new RRException("不能同一天重复接种，相同疫苗");
            }
            tChildInoculateEntity = new TChildInoculateEntity();
            tChildInoculateEntity.setChilCode(String.valueOf(m.get("chilCode")));//儿童编码
            tChildInoculateEntity.setInocBactCode(String.valueOf(vaccine.get("vaccineCode"))); //疫苗编码
            m.put("inocProperty", 0);
            tChildInoculateEntity.setInocProperty(String.valueOf(m.get("inocProperty")));//接种属性
            //自动计算儿童的接种剂次
            List<Map<String,Object>> allTimes= busBatchInoculationDao.queryCodeTimes(m.get("chilCode"),className);
            if (allTimes.size()<=0){
                m.put("times",1);
            }else{
                m.put("times",Integer.valueOf(allTimes.get(0).get("times").toString())+1);
            }
            tChildInoculateEntity.setInocTime(Integer.parseInt(m.get("times").toString()));//剂次

            tChildInoculateEntity.setInocDate(sdf.parse(m.get("inoDate").toString()));//接种日期
            tChildInoculateEntity.setInocValiddate(sdf.parse(String.valueOf(vaccine.get("expiryDate"))));//疫苗失效期//

            tChildInoculateEntity.setInocDepaCode(Constant.GLOBAL_ORG_ID);////接种单位机构编码
            tChildInoculateEntity.setInocReinforce("0");//强化属性 0:无，1：替代强化剂次，2：替代应急剂次
            tChildInoculateEntity.setInocBatchno(String.valueOf(vaccine.get("batchnum")));//批号
            tChildInoculateEntity.setInocCorpCode(String.valueOf(vaccine.get("factoryId")));//生产企业(编码)
            tChildInoculateEntity.setInocVcnKind(String.valueOf(vaccine.get("vaccineCode")).substring(0,2)); //疫苗大类编码
            if (m.get("inocRegulatoryCode")!=null) {
               tChildInoculateEntity.setInocRegulatoryCode(String.valueOf(m.get("inocRegulatoryCode")));//疫苗监管码
            }
            Double price = Double.valueOf(vaccine.get("price").toString());
            if (price > 0) {
                m.put("ismf", 0);
            } else {
                m.put("ismf", 1);
            }
            tChildInoculateEntity.setInocPay(String.valueOf(m.get("ismf")));//是否补助疫苗1是,0不是
            tChildInoculateEntity.setInocFree(String.valueOf(m.get("ismf")));////是否免费疫苗1免费,0收费

            tChildInoculateEntity.setInocCounty(Constant.GLOBAL_ORG_ID.substring(0, 6));//接种县国标(机构编码前6位)
            tChildInoculateEntity.setInocNationcode(Constant.GLOBAL_ORG_ID.substring(6));//接种单位编码(机构编码后四位)

            tChildInoculateEntity.setInocInplId(String.valueOf(m.get("inoculateSiteCode")));//接种部位(存码表value值)
            tChildInoculateEntity.setInocNurse(ShiroUtils.getUserEntity().getRealName());//接种护士
            tChildInoculateEntity.setInocEditdate(new Date());//修改记录时间
            tChildInoculateEntity.setType(3);//0:本地；1：平台,2，导入，3补录
            tChildInoculateEntity.setSyncstatus(0);//同步状态,0未同步;1同步
            tChildInoculateEntity.setStatus(0);//状态：0：正常；-1：删除
            tChildInoculateEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人编码
            tChildInoculateEntity.setCreateTime(new Date());//添加记录时间

            //联合疫苗编码   需要根据疫苗来拆分成对应的疫苗剂次
            // ..TODO
            //private String inocUnionCode;

            //根据疫苗编码和批号查询库存中是否有该疫苗


            TMgrStockInfoEntity stockInfo = new TMgrStockInfoEntity();
            stockInfo.setId(vaccine.get("id").toString());
            stockInfo.setAmount(Long.valueOf(vaccine.get("amount").toString()));
            stockInfo.setPersonAmount(Long.valueOf(vaccine.get("personAmount").toString()));
            stockInfo.setConversion(Integer.valueOf(vaccine.get("conversion").toString()));
            stockInfo.setUseAmount(Long.valueOf(vaccine.get("useAmount").toString()));
            //库存充足，扣减库存，更新库存数量。
            Long personAmount = stockInfo.getPersonAmount();
            //更新剩余人份
            stockInfo.setPersonAmount(personAmount - 1L);
            //更新使用人份
            stockInfo.setUseAmount(stockInfo.getUseAmount() + 1);
            //更新剩余数量
            if (stockInfo.getConversion() == 1 && stockInfo.getAmount() > 0) {
                stockInfo.setAmount(stockInfo.getAmount() - 1);
            } else {
                Long amount = stockInfo.getPersonAmount() / stockInfo.getConversion();
                stockInfo.setAmount(amount);
            }
            //如果剩余人份数量为0，自动归还疫苗生成归还记录
            if (stockInfo.getPersonAmount()==0){
                stockInfo.setFkBaseInfo(vaccine.get("baseId").toString());
                stockInfo.setId(vaccine.get("id").toString());
                stockInfo.setReceiveAmount(Long.valueOf(vaccine.get("receiveAmount").toString()));
                returnService.returnPersonAmountByZero(stockInfo);
            }
            infoDao.update(stockInfo);
            //保存补录信息到接种表中
            tChildInoculateDao.save(tChildInoculateEntity);

            //从查询记录中删除该信息，根据儿童，疫苗ID 剂次
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("childCode", m.get("chilCode"));
            retMap.put("planId", m.get("planId"));
            retMap.put("agentTimes", m.get("times"));
            deleteHistoryRecord(retMap);
            //初始化22针次
            initPlanService.initChildPlan(m.get("chilCode").toString());
        }
        return ret;
    }
    @Override
    public void  singleSaveInoc(Map<String,Object> m) throws Exception {
        int siteNum=busBatchInoculationDao.queryInoculateNumByMap(m);
        if (siteNum>0){
            throw new RRException("同一接种部位同一天不能重复接种");
        }

        int vacccodeNum=busBatchInoculationDao.queryInoculateVacccodeNumByMap(m);
        if (vacccodeNum>0) {
            throw new RRException("不能同一天重复接种，相同疫苗");
        }
        if (Constant.INOCULATE_TYPE_REMOVE.equals(m.get("type"))){
             singleInoculateRemove(m);
        }else if (Constant.INOCULATE_TYPE_NORMAL.equals(m.get("type"))){
            singleInoculateNormal(m);
        }
    }

    @Override
    public List<Map<String, Object>> queryVaccineBatchnum(String planId) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        TMgrStoreEntity towerStore = null;
        if (user.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
            towerStore = store.selectByUserId(user.getUserId());
        } else {
            String towerId = user.getInoculateTowerId();
            towerStore=store.selectByTowerId(towerId);
        }
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (towerStore!=null) {
           hashMap.put("storeId",towerStore.getId());
        }
        hashMap.put("planId",planId);
        List<Map<String, Object>> batchums = infoDao.queryVaccineBatchum(hashMap);
        if (batchums==null||batchums.size()==0){
            hashMap.remove("storeId");
            batchums=infoDao.queryVaccineBatchum(hashMap);
        }
        return  batchums;
    }

    @Override
    public String[] queryInoculateSiteList() {
        return sysDictDao.queryInoculateSiteList();
    }

    @Override
    public int changeChildInfo(List<Map<String, Object>> rows) {
        if(rows.size()>0){
            for (Map<String, Object> row : rows) {
                //查询原信息
                TChildInfoEntity child = childInfoDao.queryObject(row.get("chilCode"));
                String here = switchHere(row.get("here").toString())+"";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String now = sdf.format(new Date());
                String updateTime = sdf.format(child.getUpdateTime());
                if(!here.equals(child.getChilHere())&&!now.equals(updateTime)){
                    TChildMoveEntity moveEntity = new TChildMoveEntity();
                    moveEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
                    moveEntity.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
                    moveEntity.setOrgId(Constant.GLOBAL_ORG_ID);
                    moveEntity.setChheDepaId(Constant.GLOBAL_ORG_ID);
                    moveEntity.setChhePrehere(child.getChilHere());
                    moveEntity.setChheHere(here);
                    moveEntity.setChheChilProvinceremark("村医EXCEL导入变更");
                    moveEntity.setChilCode(child.getChilCode());
                    tChildMoveDao.save(moveEntity);
                    child.setChilHere(here);
                    if(!StringUtils.isEmpty(row.get("remark"))){
                        child.setRemark(row.get("remark").toString());
                    }
                    childInfoDao.update(child);
                }

                if(here.equals(child.getChilHere())&&!StringUtils.isEmpty(row.get("remark"))&&!row.get("remark").equals(child.getRemark())&&!now.equals(updateTime)){
                    child.setRemark(row.get("remark").toString());
                    childInfoDao.update(child);
                }

            }
            return 1;
        }

        return 0;
    }

    private void singleInoculateRemove(Map<String, Object> m) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //获取当前选择的接种台仓库ID
        Map<String, Object> ret = new HashMap<>();
        SysUserEntity user = ShiroUtils.getUserEntity();
        TMgrStoreEntity towerStore =null;
        if (user.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
            towerStore = store.selectByUserId(user.getUserId());
        } else {
            String towerId = user.getInoculateTowerId();
             towerStore = store.selectByTowerId(towerId);
            if (towerStore == null) {
                throw new RRException("该用户无权进行录入操作，请切换用户！");
            }
        }
        //查询库存是否足够

        //根据疫苗分类ID和仓库ID查询出所有批号的疫苗，按疫苗是否收费，疫苗失效期扣减库存，优先扣减免费，失效期短的库存
        List<Map<String, Object>> vaccines = infoDao.queryByCodeAndStore(m.get("vaccineCode").toString(), towerStore.getId(),String.valueOf(m.get("batchnum")));
        if (vaccines.size() <= 0) {
            throw new RRException("没有领取疫苗或库存不足，请领取疫苗之后再继续操作！");
        }
        Map<String, Object> vaccine = vaccines.get(0);
        TChildInoculateEntity tChildInoculateEntity = null;

        tChildInoculateEntity = new TChildInoculateEntity();
        tChildInoculateEntity.setChilCode(String.valueOf(m.get("chilCode")));//儿童编码
        tChildInoculateEntity.setInocBactCode(String.valueOf(vaccine.get("vaccineCode"))); //疫苗编码
        tChildInoculateEntity.setInocVcnKind(String.valueOf(vaccine.get("vaccineCode")).substring(0,2)); //疫苗大类编码
        m.put("inocProperty", 0);
        tChildInoculateEntity.setInocProperty(String.valueOf(m.get("inocProperty")));//接种属性
        //自动计算儿童的接种剂次
        List<Map<String,Object>> allTimes= busBatchInoculationDao.queryCodeTimes(m.get("chilCode"),vaccine.get("vaccineCode").toString().substring(0,2));
        if (allTimes.size()<=0){
            m.put("times",1);
        }else{
            m.put("times",Integer.valueOf(allTimes.get(0).get("times").toString())+1);
        }
        tChildInoculateEntity.setInocTime(Integer.parseInt(m.get("times").toString()));//剂次
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        tChildInoculateEntity.setInocDate(sf.parse(m.get("inoDate").toString()));//接种日期
        tChildInoculateEntity.setInocValiddate(sdf.parse(String.valueOf(vaccine.get("expiryDate"))));//疫苗失效期//

        tChildInoculateEntity.setInocDepaCode(Constant.GLOBAL_ORG_ID);////接种单位机构编码
        tChildInoculateEntity.setInocReinforce("0");//强化属性 0:无，1：替代强化剂次，2：替代应急剂次
        tChildInoculateEntity.setInocBatchno(String.valueOf(m.get("batchnum")));//批号
        tChildInoculateEntity.setInocCorpCode(String.valueOf(vaccine.get("factoryId")));//生产企业(编码)
        if (m.get("inocRegulatoryCode")!=null) {
            tChildInoculateEntity.setInocRegulatoryCode(String.valueOf(m.get("inocRegulatoryCode")));//疫苗监管码
        }
        Double price = Double.valueOf(vaccine.get("price").toString());
        if (price > 0) {
            m.put("ismf", 0);
        } else {
            m.put("ismf", 1);
        }
        tChildInoculateEntity.setInocPay(String.valueOf(m.get("ismf")));//是否补助疫苗1是,0不是
        tChildInoculateEntity.setInocFree(String.valueOf(m.get("ismf")));////是否免费疫苗1免费,0收费

        tChildInoculateEntity.setInocCounty(Constant.GLOBAL_ORG_ID.substring(0, 6));//接种县国标(机构编码前6位)
        tChildInoculateEntity.setInocNationcode(Constant.GLOBAL_ORG_ID.substring(6));//接种单位编码(机构编码后四位)

        tChildInoculateEntity.setInocInplId(String.valueOf(m.get("inoculateSiteCode")));//接种部位(存码表value值)
        tChildInoculateEntity.setChannel(String.valueOf(m.get("channel")));//接种途径(存码表value值)
        tChildInoculateEntity.setInocNurse(ShiroUtils.getUserEntity().getRealName());//接种护士
        tChildInoculateEntity.setInocEditdate(new Date());//修改记录时间
        tChildInoculateEntity.setType(3);//0:本地；1：平台,2，导入，3补录
        tChildInoculateEntity.setSyncstatus(0);//同步状态,0未同步;1同步
        tChildInoculateEntity.setStatus(0);//状态：0：正常；-1：删除
        tChildInoculateEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人编码
        tChildInoculateEntity.setCreateTime(new Date());//添加记录时间

        TMgrStockInfoEntity stockInfo = new TMgrStockInfoEntity();
        stockInfo.setId(vaccine.get("id").toString());
        stockInfo.setAmount(Long.valueOf(vaccine.get("amount").toString()));
        stockInfo.setPersonAmount(Long.valueOf(vaccine.get("personAmount").toString()));
        stockInfo.setConversion(Integer.valueOf(vaccine.get("conversion").toString()));
        stockInfo.setUseAmount(Long.valueOf(vaccine.get("useAmount").toString()));
        //库存充足，扣减库存，更新库存数量。
        Long personAmount = stockInfo.getPersonAmount();
        //更新剩余人份
        stockInfo.setPersonAmount(personAmount - 1L);
        //更新使用人份
        stockInfo.setUseAmount(stockInfo.getUseAmount() + 1);
        //更新剩余数量
        if (stockInfo.getConversion() == 1 && stockInfo.getAmount() > 0) {
            stockInfo.setAmount(stockInfo.getAmount() - 1);
        } else {
            Long amount = stockInfo.getPersonAmount() / stockInfo.getConversion();
            stockInfo.setAmount(amount);
        }
        //如果剩余人份数量为0，自动归还疫苗生成归还记录
        if (stockInfo.getPersonAmount()==0){
            stockInfo.setFkBaseInfo(vaccine.get("baseId").toString());
            stockInfo.setId(vaccine.get("id").toString());
            stockInfo.setReceiveAmount(Long.valueOf(vaccine.get("receiveAmount").toString()));
            returnService.returnPersonAmountByZero(stockInfo);
        }
        infoDao.update(stockInfo);
        //保存补录信息到接种表中
        tChildInoculateDao.save(tChildInoculateEntity);

        //从查询记录中删除该信息，根据儿童，疫苗ID 剂次
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("childCode", m.get("chilCode"));
        retMap.put("planId", m.get("planId"));
        retMap.put("agentTimes", m.get("times"));
        deleteHistoryRecord(retMap);
        //初始化22针次
        initPlanService.initChildPlan(m.get("chilCode").toString());

    }

    private void singleInoculateNormal(Map<String,Object> m) throws ParseException{
        TChildInoculateEntity tChildInoculateEntity = null;

        tChildInoculateEntity = new TChildInoculateEntity();
        tChildInoculateEntity.setChilCode(String.valueOf(m.get("chilCode")));//儿童编码
        tChildInoculateEntity.setInocBactCode(String.valueOf(m.get("vaccineCode"))); //疫苗编码
        tChildInoculateEntity.setInocVcnKind(String.valueOf(m.get("vaccineCode")).substring(0,2)); //疫苗大类编码
        m.put("inocProperty", 0);
        tChildInoculateEntity.setInocProperty(String.valueOf(m.get("inocProperty")));//接种属性
        //自动计算儿童的接种剂次
        List<Map<String,Object>> allTimes= busBatchInoculationDao.queryCodeTimes(m.get("chilCode"),m.get("vaccineCode").toString().substring(0,2));
        if (allTimes.size()<=0){
            m.put("times",1);
        }else{
            m.put("times",Integer.valueOf(allTimes.get(0).get("times").toString())+1);
        }
        tChildInoculateEntity.setInocTime(Integer.parseInt(m.get("times").toString()));//剂次
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        tChildInoculateEntity.setInocDate(sf.parse(m.get("inoDate").toString()));//接种日期
        tChildInoculateEntity.setInocValiddate(sf.parse(String.valueOf(m.get("expiryDate"))));//疫苗失效期//
        if (!StringUtils.isEmpty(m.get("orgId"))){
            String orgId = m.get("orgId").toString();
            tChildInoculateEntity.setInocDepaCode(orgId);////接种单位机构编码
            tChildInoculateEntity.setInocCounty(orgId.substring(0, 6));//接种县国标(机构编码前6位)
            tChildInoculateEntity.setInocNationcode(orgId.substring(6));//接种单位编码(机构编码后四位)
        }
        //本接种点录入接种医生
        if (!StringUtils.isEmpty(m.get("orgId"))&&m.get("orgId").equals(ShiroUtils.getUserEntity().getOrgId())){
            tChildInoculateEntity.setInocNurse(ShiroUtils.getUserEntity().getRealName());//接种护士
        }
        tChildInoculateEntity.setInocReinforce("0");//强化属性 0:无，1：替代强化剂次，2：替代应急剂次
        if (!StringUtils.isEmpty(m.get("batchnum"))){
             tChildInoculateEntity.setInocBatchno(String.valueOf(m.get("batchnum")));//批号
        }
        if (!StringUtils.isEmpty(m.get("factoryId"))){
           tChildInoculateEntity.setInocCorpCode(String.valueOf(m.get("factoryId")));//生产企业(编码)
        }
        if (m.get("inocRegulatoryCode")!=null) {
            tChildInoculateEntity.setInocRegulatoryCode(String.valueOf(m.get("inocRegulatoryCode")));//疫苗监管码
        }
        if (!StringUtils.isEmpty(m.get("otherDepa"))) {
            tChildInoculateEntity.setOtherDepa(String.valueOf(m.get("otherDepa")));//省外地址
        }
        /*Double price = Double.valueOf(vaccine.get("price").toString());
        if (price > 0) {
            m.put("ismf", 0);
        } else {
            m.put("ismf", 1);
        }*/
        tChildInoculateEntity.setInocPay("1");//是否补助疫苗1是,0不是
        tChildInoculateEntity.setInocFree("1");////是否免费疫苗1免费,0收费
        tChildInoculateEntity.setInocInplId(String.valueOf(m.get("inoculateSiteCode")));//接种部位(存码表value值)
        tChildInoculateEntity.setChannel(String.valueOf(m.get("channel")));//接种途径(存码表value值)
        tChildInoculateEntity.setInocEditdate(new Date());//修改记录时间
        tChildInoculateEntity.setType(3);//0:本地；1：平台,2，导入，3补录
        tChildInoculateEntity.setSyncstatus(0);//同步状态,0未同步;1同步
        tChildInoculateEntity.setStatus(0);//状态：0：正常；-1：删除
        tChildInoculateEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人编码
        tChildInoculateEntity.setCreateTime(new Date());//添加记录时间


        //保存补录信息到接种表中
        tChildInoculateDao.save(tChildInoculateEntity);

        //初始化22针次
        initPlanService.initChildPlan(m.get("chilCode").toString());

    }

    @Override
    public int deleteHistoryRecord(Map param) {
        int ret=busBatchInoculationDao.deleteHistoryRecord(param);
        return 0;
    }

    @Override
    public Map<String, Object> uploadFile(MultipartFile file, String uploadfilepath) {
        Map<String,Object> result=new HashMap<>();

        if(!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File newfile = new File(uploadfilepath,filename);
            File filesPath = new File(uploadfilepath);
            //判断文件是否已经存在 存在则删除
            if(newfile.exists()){
                newfile.delete();
            }
            //判断路径是否存在，如果不存在就创建一个
            if (!filesPath.getParentFile().exists()) {
                filesPath.getParentFile().mkdirs();
            }
            FileOutputStream out =null;
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(),newfile);
                List<Map<String, Object>> list = loadExcel(newfile.toString());
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String name1= MapUtils.getString(o1, "isInoc");
                        String name2=MapUtils.getString(o2, "isInoc");
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        return instance.compare(name1, name2);
                    }
                });
                Collections.reverse(list);
                result.put("message","上传成功");
                result.put("data",list);
                return result;
            } catch (RRException e) {
                e.printStackTrace();
                result.put("message",e.getMsg());
                return result;
            }catch (Exception e) {
                e.printStackTrace();
                result.put("message","上传失败,系统异常");
                return result;
            }
        } else {
            result.put("message","文件是空的");
            return result;
        }
    }

    @Override
    public String[] getAllVaccine() {
        return  busBatchInoculationDao.getAllVaccine();
    }

    @Override
    public String[] queryChildInfoStatus() {
        return  busBatchInoculationDao.queryChildInfoStatus();
    }

    private  List<Map<String,Object>>  loadExcel(String filePath) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();

        // 得到这个excel表格对象
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
        HSSFSheet sheet = wb.getSheetAt(0); //得到第一个sheet
        int rowNo = sheet.getLastRowNum(); //得到行数
        try {
            for (int i = 3; i < rowNo + 1; i++) {
                HSSFRow row = sheet.getRow(i);
                HSSFCell cell0 = row.getCell((short) 0);
                HSSFCell cell1 = row.getCell((short) 1);
                HSSFCell cell2 = row.getCell((short) 2);
                HSSFCell cell3 = row.getCell((short) 3);
                HSSFCell cell4 = row.getCell((short) 4);
                HSSFCell cell5 = row.getCell((short) 5);
                HSSFCell cell6 = row.getCell((short) 6);
                HSSFCell cell7 = row.getCell((short) 7);
                HSSFCell cell8 = row.getCell((short) 8);
                HSSFCell cell9 = row.getCell((short) 9);
                HSSFCell cell10 = row.getCell((short) 10);
                HSSFCell cell11 = row.getCell((short) 11);
                HSSFCell cell12 = row.getCell((short) 12);
                HSSFCell cell13 = row.getCell((short) 13);
                HSSFCell cell14 = row.getCell((short) 14);
                HSSFCell cell15 = row.getCell((short) 15);
                HSSFCell cell16 = row.getCell((short) 16);
                HSSFCell cell17 = row.getCell((short) 17);
                Map<String, Object> temp = new HashMap<>();

                for (int j = 0; j < 18; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    }

                }
                if (cell1 != null && !StringUtils.isEmpty(cell1.getStringCellValue())
                        && cell1.getStringCellValue().trim().length() == 18
                        && cell17 != null && !StringUtils.isEmpty(cell17.getStringCellValue()) ) {//&& cell17.getStringCellValue().equals("是")
                    temp.put("id", UUID.randomUUID().toString());
                    temp.put("committee", cell0 == null ? "" : cell0.getStringCellValue().trim());
                    temp.put("chilCode", cell1 == null ? "" : cell1.getStringCellValue().trim());
                    temp.put("chilName", cell2 == null ? "" : cell2.getStringCellValue().trim());
                    temp.put("chilSex", cell3 == null ? "" : cell3.getStringCellValue().trim());
                    temp.put("chilBirthday", cell4 == null ? "" : cell4.getStringCellValue().trim());
                    temp.put("fatherName", cell5 == null ? "" : cell5.getStringCellValue().trim());
                    temp.put("matherName", cell6 == null ? "" : cell6.getStringCellValue().trim());
                    temp.put("chilTel", cell7 == null ? "" : cell7.getStringCellValue().trim());
                    temp.put("chilMobile", cell8 == null ? "" : cell8.getStringCellValue().trim());
                    temp.put("address", cell9 == null ? "" : cell9.getStringCellValue().trim());
                    temp.put("planName", cell10 == null ? "" : cell10.getStringCellValue().trim());
                    if (cell12 != null) {
                            Integer.valueOf(cell12.getStringCellValue().trim());
                    }
                    temp.put("times", cell12 == null ? "" : cell12.getStringCellValue().trim());
                    temp.put("inoDate", "");
                    if (cell13 != null) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                        String trim = "";
                        if (!cell13.getStringCellValue().startsWith("2") && cell13.getStringCellValue().indexOf("-") < 0) {
                            Calendar calendar = new GregorianCalendar(1900, 0, -1);
                            Date d = calendar.getTime();
                            Date dd = org.apache.commons.lang.time.DateUtils.addDays(d, Integer.valueOf(cell13.getStringCellValue()));
                            trim = sdf.format(dd);
                        } else {
                            trim = cell13.getStringCellValue().trim();
                        }

                        if (trim.indexOf("-") > 0) {
                            sdf.parse(trim);
                        } else {
                            Date inocDate = sf.parse(trim);
                            trim = sdf.format(inocDate);

                        }
                        temp.put("inoDate", trim);


                    }
                    temp.put("batchnum", cell11.getStringCellValue() == null ? "" : cell11.getStringCellValue().trim());
                    if (!StringUtils.isEmpty(cell14.getStringCellValue())) {
                        Map<String, Object> queryTemp = new HashMap<>();
                        queryTemp.put("text", cell14.getStringCellValue());
                        queryTemp.put("type", "code_inoculation_site");
                        String val = sysDictDao.queryValueByText(queryTemp);
                        temp.put("inoculateSiteCode", val);
                    }

                    temp.put("here", cell15 == null ? "" : cell15.getStringCellValue().trim());
                    temp.put("remark", cell16 == null ? "" : cell16.getStringCellValue().trim());
                    temp.put("isInoc", "");
                    if (cell17 != null) {
                        String trim = cell17.getStringCellValue().trim();
                        if (!trim.equals("是") && !trim.equals("否")) {
                            throw new RRException("上传失败，是否接种格式不正确，请检查数据");
                        }
                        temp.put("isInoc", cell17.getStringCellValue().trim());
                    }
                    list.add(temp);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RRException("上传失败，接种日期格式不正确，请检查数据");
        }catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RRException("上传失败，剂次不是数字格式不正确，请检查数据");
        }

        return list;
    }
    private int switchHere(String hereName){
     /*   临时外转	3
        临时接种	9
        入学	6
        本地	1
        异地接种	8
        入托	7
        死亡	4
        外地转来	10
        删除	5
        迁出	2*/
        switch (hereName){
            case "本地":return 1;
            case "迁出":return 2;
            case "临时外转":return 3;
            case "死亡":return 4;
            case "删除":return 5;
            case "入学":return 6;
            case "入托":return 7;
            case "异地接种":return 8;
            case "临时接种":return 9;
            case "外地转来":return 10;
            default:return 0;
        }
    }
}
