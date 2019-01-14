package io.yfjz.service.child.impl;

import io.yfjz.dao.bus.TBusRegisterDao;
import io.yfjz.dao.child.TChildInoculateDao;
import io.yfjz.dao.mgr.TMgrStockInfoDao;
import io.yfjz.dao.mgr.TMgrStoreDao;
import io.yfjz.dao.sys.SysDepartUserDao;
import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.entity.mgr.TMgrUseRecord;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.service.bus.ReturnService;
import io.yfjz.service.bus.VaccRegisterService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.quartz.ListenerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.resource.spi.RetryableUnavailableException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * 儿童接种记录表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 14:02:29
 */
@Service("tChildInoculateService")
public class TChildInoculateServiceImpl implements TChildInoculateService {

    private static Logger logger=Logger.getLogger(TChildInoculateServiceImpl.class);
    private
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private TChildInoculateDao tChildInoculateDao;
    @Autowired
    private SysDepartUserDao departUserDao;

    @Autowired
    TQueueNoService tQueueNoService;

    @Autowired
    InitPlanService initPlanService;

    @Autowired
    TBusRegisterDao tBusRegisterDao;
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private TMgrStoreDao store;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private TMgrStockInfoDao infoDao;

    @Override
    public TChildInoculateEntity queryObject(String id){
        return tChildInoculateDao.queryObject(id);
    }

    @Override
    public TChildInoculateEntity queryLastInoObject(String id) {
        return tChildInoculateDao.queryLastInoObject(id);
    }

    @Override
    public List<TChildInoculateEntity> queryList(Map<String, Object> map){
        return tChildInoculateDao.queryList(map);
    }

    @Override
    public List<TChildInoculateEntity> querydoses(Map<String, Object> map){
        return tChildInoculateDao.querydoses(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return tChildInoculateDao.queryTotal(map);
    }

    @Override
    public R batchInsertAccountInfo(TChildInoculateEntity tChildInoculate) {
        try {
            tChildInoculate.setInocVcnKind(tChildInoculate.getInocBactCode().substring(0, 2));
        }catch (Exception e){
            logger.error("疫苗有误");
        }
        //计算接种剂次
        Map<String,Object> map  = new HashMap<>();
        map.put("chilCode", tChildInoculate.getChilCode());
        map.put("inocBactCode", tChildInoculate.getInocBactCode());
        List<TChildInoculateEntity> tChildInoculateEntity = tChildInoculateDao.querydoses(map);
        if(tChildInoculateEntity.size()==0 || tChildInoculateEntity==null){
            if(tChildInoculate.getInocTime()==null || "".equals(tChildInoculate.getInocTime())){
                tChildInoculate.setInocTime(tChildInoculate.getInocTime()==null|| String.valueOf(tChildInoculate.getInocTime())=="" ? 1 : tChildInoculate.getInocTime()+1);
            }
        }else {
            for (TChildInoculateEntity list1 : tChildInoculateEntity) {
                if (tChildInoculate.getInocTime() == null || "".equals(tChildInoculate.getInocTime())) {
                    Date bt = list1.getInocDate();
                    Date et = tChildInoculate.getInocDate();
                    if (bt.before(et)) {
                        tChildInoculate.setInocTime(tChildInoculateEntity.size() + 1);
                        System.out.println(tChildInoculateEntity.size() + 1);
                        //表示bt小于et
                    } else {
                        tChildInoculate.setInocTime(tChildInoculateEntity.size() + 1);
                        System.out.println(tChildInoculateEntity.size() + 1);
                    }
                } else if (tChildInoculate.getInocTime() == list1.getInocTime()) {
                    return R.error("记录存在");
                }
            }
        }
        tChildInoculate.setCreateTime(new Date());
        tChildInoculate.setType(3);//补录产生的数据
        tChildInoculate.setCreateUserId(ShiroUtils.getUserId());
//        tChildInoculate.setOtherDepa(tChildInoculate.getInocDepaCode());//接种单位机构编码


        String[] string = tChildInoculate.getInocDepaCode().split("-");

        String strdepacode="";
        try {
            if (string.length == 0) {
                strdepacode = string[0];
                tChildInoculate.setOtherDepa(strdepacode);//接种单位机构编码
                tChildInoculate.setInocCounty("");//接种县6位
                tChildInoculate.setInocNationcode("");//后4位
            } else {
                strdepacode = string[1];
                tChildInoculate.setInocDepaCode(strdepacode);//接种单位机构编码
                tChildInoculate.setInocCounty(strdepacode.substring(0,6));//接种县6位
                tChildInoculate.setInocNationcode(strdepacode.substring(6));//后4位
            }
        }catch (Exception e){
            strdepacode = string[0];
            tChildInoculate.setOtherDepa(strdepacode);//接种单位机构编码
            tChildInoculate.setInocCounty("");//接种县6位
            tChildInoculate.setInocNationcode("");//后4位
            e.printStackTrace();
        }
        tChildInoculateDao.batchInsertAccountInfo(tChildInoculate);
        initPlanService.initChildPlan(tChildInoculate.getChilCode());
        return R.ok();
    }
    @Override
    public R batchInsertAccountInfos(TChildInoculateEntity tChildInoculate) {
        try {
            tChildInoculate.setInocVcnKind(tChildInoculate.getInocBactCode().substring(0, 2));
        }catch (Exception e){
            logger.error("疫苗有误");
        }

        //计算接种剂次
        Map<String,Object> map  = new HashMap<>();
        map.put("chilCode", tChildInoculate.getChilCode());
        map.put("inocBactCode", tChildInoculate.getInocBactCode());
        List<TChildInoculateEntity> tChildInoculateEntity = tChildInoculateDao.querydoses(map);
        Map<String,Object> map2 = new HashMap<>();
        for (TChildInoculateEntity list1:tChildInoculateEntity){
            if(tChildInoculate.getInocTime()==list1.getInocTime()){
                return R.error("记录存在"+list1.getInocBactCode());
            }
        }
        if(tChildInoculate.getInocDepaCode().equals("3")){
            tChildInoculate.setInocDepaCode(ShiroUtils.getUserEntity().getOrgId());
        }else{
            tChildInoculate.setOtherDepa(tChildInoculate.getInocDepaCode());
        }
        tChildInoculate.setChilCode(tChildInoculate.getChilCode());
        tChildInoculate.setInocTime(tChildInoculate.getInocTime());
        tChildInoculate.setInocDate(tChildInoculate.getInocDate());
        tChildInoculate.setInocBactCode(tChildInoculate.getInocBactCode());
        tChildInoculate.setCreateTime(new Date());
        tChildInoculate.setType(3);//补录产生的数据
        tChildInoculate.setCreateUserId(ShiroUtils.getUserId());
        tChildInoculateDao.batchInsertAccountInfo(tChildInoculate);
        initPlanService.initChildPlan(tChildInoculate.getChilCode());
        return R.ok();
    }

    @Override
    public List<TChildInoculateEntity> queryListdoses(Map<String, Object> map) {
        return tChildInoculateDao.queryListdoses(map);
    }


    @Override
    public void save(TChildInoculateEntity tChildInoculate){
        tChildInoculate.setCreateTime(new Date());
        tChildInoculate.setCreateUserId(ShiroUtils.getUserId());
        tChildInoculate.setInocDate(new Date());
        SysUserEntity sysUserEntity = departUserDao.queryOrgInfoByUserId(ShiroUtils.getUserId());
        tChildInoculate.setInocDepaCode(sysUserEntity.getOrgId());//接种单位机构编码
        tChildInoculate.setInocCounty(sysUserEntity.getOrgId().substring(0,6));//接种县6位
        tChildInoculate.setInocNationcode(sysUserEntity.getOrgId().substring(6));//后4位
        tChildInoculate.setInocNurse(ShiroUtils.getUserEntity().getRealName());//接种护士
        tChildInoculate.setLeaveTime(new Date());
        tChildInoculateDao.save(tChildInoculate);
    }

    @Override
    public void update(TChildInoculateEntity tChildInoculate){
        tChildInoculateDao.update(tChildInoculate);
        try{
            initPlanService.initChildPlan(tChildInoculate.getChilCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id){
        tChildInoculateDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids){
        tChildInoculateDao.deleteBatch(ids);
    }

    @Override
    public R finished(List<Map> mapList) {

        try {
            TChildInoculateEntity tChildInoculateEntity = null;
            Map m = mapList.get(0);

            //四：国家免疫规划疫苗同时接种原则 1:两种及以上注射类疫苗应在不同部位接种
            int siteNum=tChildInoculateDao.queryInoculateNumByMap(m);
            if (siteNum>0){
                return R.error("同一接种部位同一天不能重复接种");
            }

            int vacccodeNum=tChildInoculateDao.queryInoculateVacccodeNumByMap(m);
            if (vacccodeNum>0) {
                return R.error("该疫苗今日已接种，不能重复接种");
            }

            tChildInoculateEntity = new TChildInoculateEntity();
            tChildInoculateEntity.setChilCode(String.valueOf(m.get("childCode")));//儿童编码
            tChildInoculateEntity.setInocBactCode(String.valueOf(m.get("bioCode"))); //疫苗编码
            tChildInoculateEntity.setInocVcnKind(String.valueOf(m.get("bioCode")).substring(0,2)); //疫苗大类编码
            tChildInoculateEntity.setInocProperty(String.valueOf(m.get("inocProperty")));//接种属性
            tChildInoculateEntity.setInocTime(Integer.parseInt(m.get("doseNo").toString()));//剂次
            tChildInoculateEntity.setInocDate(new Date());//接种日期
            tChildInoculateEntity.setChannel(m.get("channel").toString());//添加接种途径字段
            tChildInoculateEntity.setInocDepaCode(Constant.GLOBAL_ORG_ID);////接种单位机构编码
            tChildInoculateEntity.setInocReinforce("0");//强化属性 0:无，1：替代强化剂次，2：替代应急剂次
            tChildInoculateEntity.setInocBatchno(String.valueOf(m.get("batchnum")));//批号
            tChildInoculateEntity.setInocValiddate(df.parse(String.valueOf(m.get("expiryDate"))));//疫苗失效期//
            tChildInoculateEntity.setInocCorpCode(String.valueOf(m.get("factoryCode")));//生产企业(编码)
            tChildInoculateEntity.setInocRegulatoryCode(String.valueOf(m.get("inocRegulatoryCode")));//疫苗监管码
            tChildInoculateEntity.setInocPay(String.valueOf(m.get("ismf")));//是否补助疫苗1是,0不是
            tChildInoculateEntity.setInocFree(m.get("ismf").toString());////是否免费疫苗1免费,0收费
            tChildInoculateEntity.setInocCounty(Constant.GLOBAL_ORG_ID.substring(0,6));//接种县国标(机构编码前6位)
            tChildInoculateEntity.setInocNationcode(Constant.GLOBAL_ORG_ID.substring(6));//接种单位编码(机构编码后四位)
            tChildInoculateEntity.setInocInplId(String.valueOf(m.get("inoculateSiteCode")));//接种部位(存码表value值)
            tChildInoculateEntity.setInocNurse(ShiroUtils.getUserEntity().getRealName());//接种护士
            tChildInoculateEntity.setInocEditdate(new Date());//修改记录时间
            tChildInoculateEntity.setType(0);//数据来源：0:本地；1：平台; 2：导数据；3：补录
            tChildInoculateEntity.setSyncstatus(0);//同步状态,0未同步;1同步
            tChildInoculateEntity.setStatus(0);//状态：0：正常；-1：删除
            tChildInoculateEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人编码
            tChildInoculateEntity.setCreateTime(new Date());//添加记录时间
            tChildInoculateEntity.setRemark(String.valueOf(m.get("remark")));//备注

            //联合疫苗编码   需要根据疫苗来拆分成对应的疫苗剂次
            // ..TODO
            //private String inocUnionCode;

            //查询库存并扣减，如果失败，throw new RRException();
            //获取当前选择的接种台仓库ID
            SysUserEntity user = ShiroUtils.getUserEntity();
            TMgrStoreEntity towerStore = null;
            if (user.getRoleNames().contains(Constant.ROLE_COUNTRY)) {
                towerStore = store.selectByUserId(user.getUserId());
            } else {
                String towerId = user.getInoculateTowerId();
                 towerStore=store.selectByTowerId(towerId);
                if (towerStore==null){
                    return R.error("该接种台未绑定仓库，请绑定仓库后操作！");
                }
            }

            //根据疫苗编码和批号查询库存中是否有该疫苗
            String bioCode = m.get("bioCode").toString();
            String batchnum = m.get("batchnum").toString();
            Map<Object, Object> tempMap = new HashMap<>();
            tempMap.put("bioCode",bioCode);
            tempMap.put("batchnum",batchnum);
            if(m.get("storeId")!=null&&!"".equals(m.get("storeId"))){
                tempMap.put("storeId",m.get("storeId"));
            }else{
                tempMap.put("storeId",towerStore.getId());
            }
            TMgrStockInfoEntity stockInfo = infoDao.queryListByCodeIdAndBatchnum(tempMap);
            if (stockInfo==null||stockInfo.getPersonAmount()<=0){
                return R.error("没有领取疫苗"+m.get("bioName")+"或库存不足，请领取疫苗之后再继续操作！");
            }
            //库存充足，扣减库存，更新库存数量。
            Long personAmount = stockInfo.getPersonAmount();
            //更新剩余人份
            stockInfo.setPersonAmount(personAmount-1L);
            //更新使用人份
            stockInfo.setUseAmount(stockInfo.getUseAmount()+1);
            //更新剩余数量
            if (stockInfo.getConversion()==1&&stockInfo.getAmount()>0){
                stockInfo.setAmount(stockInfo.getAmount()-1);
            }else{
                Long amount= stockInfo.getPersonAmount()/stockInfo.getConversion();
                stockInfo.setAmount(amount);
            }
            //如果剩余人份数量为0，自动归还疫苗生成归还记录

            //如果使用的是其他接种台领取的疫苗，单独生成使用记录
            if(m.get("storeId")!=null&&!"".equals(m.get("storeId"))){
                TMgrUseRecord useRecord = new TMgrUseRecord();
                useRecord.setCreateTime(new Date());
                useRecord.setCreateUserId(user.getUserId());
                useRecord.setNowTowerId(user.getInoculateTowerId());
                useRecord.setReceiveStoreId(m.get("storeId").toString());
                useRecord.setUsePersonAmount(1L);
                useRecord.setBaseInfoId(stockInfo.getFkBaseInfo());
                useRecord.setDeleteStatus(0);
                infoDao.insertUseRecord(useRecord);
            }
            if (stockInfo.getPersonAmount()==0){
                returnService.returnPersonAmountByZero(stockInfo);
            }else{
                infoDao.update(stockInfo);
            }
            //保存登记疫苗信息到接种表中
            tChildInoculateDao.save(tChildInoculateEntity);

            //t_bus_register根据id更新状态 注册完成
            tBusRegisterDao.updateStatus(new HashMap<String, Object>() {{
                put("id", m.get("id"));
                put("status", "1");//登记疫苗已完成接种
            }});
            //判断今日登记的儿童登记的疫苗是否已经完全接种完毕
            Map countMap = tBusRegisterDao.countTodayRegister(m.get("childCode").toString());
            logger.info("查询当前待登记的疫苗数量countMap数据{}====="+countMap.get("totalCount").toString());
            if (countMap == null || countMap.get("totalCount").toString().equals("0")) {
                //已登记的疫苗都注射了，调用队列将数据推送到留观室
                //调用队列通知接口
                tQueueNoService.finishedCurrentStep("inoculate", m.get("sequenceNoId").toString(), m.get("childCode").toString(), m.get("childName")==null?null:m.get("childName").toString());
            }
            //初始化规划表
            initPlanService.initChildPlan(m.get("childCode").toString());
            return R.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统异常,请联系管理员{}"+e.getMessage());
        }
    }

    /**
     * 接种信息完整性统计
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/22 15:49
    */
    @Override
    public List<InoculateIntegrityRateEntity> listInocIntegrityRate(Map<String, Object> map) {
        final Object inoVacc = map.get("inoVacc");//疫苗
        final Object obj_childInfoStatus = map.get("childInfoStatus");//在册情况
        final Object obj_chilResidence = map.get("chilResidence");//居住属性
        final Object obj_chilAccount = map.get("chilAccount");//户籍属性
        final Object obj_chilCommittees = map.get("chilCommittees");//居委会/行政村

        final Map<String,Object> params = new HashMap<String,Object>();
        if(inoVacc != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])inoVacc);
            } catch (Exception e) {
                list = Arrays.asList(inoVacc.toString());
            }
            params.put("bios",list);
        }
        if(obj_childInfoStatus != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_childInfoStatus);
            } catch (Exception e) {
                list = Arrays.asList(obj_childInfoStatus.toString());
            }
            params.put("childInfoStatus",list);
        }
        if(obj_chilResidence != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilResidence);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilResidence.toString());
            }
            params.put("chilResidence",list);
        }
        if(obj_chilAccount != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilAccount);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilAccount.toString());
            }
            params.put("chil_account",list);
        }
        if(obj_chilCommittees != null){
            List<String> list = new ArrayList<>();
            try {
                list = Arrays.asList((String[])obj_chilCommittees);
            } catch (Exception e) {
                list = Arrays.asList(obj_chilCommittees.toString());
            }
            params.put("committee",list);
        }
        params.put("inocDateStart",map.get("inocDateStart"));
        params.put("inocDateEnd",map.get("inocDateEnd"));
        params.put("birthDayStart",map.get("birthDayStart"));
        params.put("birthDayEnd",map.get("birthDayEnd"));
        params.put("org_id",map.get("org_id"));
        params.put("child_code",map.get("child_code"));//儿童编号
        return tChildInoculateDao.getInocIntegrityRateByCondition(params);
    }

    @Override
    public int queryAllTotal() {
        return tChildInoculateDao.queryAllTotal();
    }

    @Override
    public List<Map<String, Object>> queryobservation(String notext) {
        List<Map<String, Object>> queryobservation = tChildInoculateDao.queryobservation(notext);
        return queryobservation;
    }
    @Override
    public List<Map<String, Object>> queryhistoryobservation(String chilCode) {
        List<Map<String, Object>> queryobservation = tChildInoculateDao.queryhistoryobservation(chilCode);
        return queryobservation;
    }

    @Override
    public List<Map<String, Object>> getchoolAdmission(String chilCode) {
        List<Map<String, Object>> inoList = null;
        try {
            inoList = tChildInoculateDao.schoolAdmissionIno(chilCode);
        }catch (Exception e){
        }
        return inoList;
    }

    @Override
    public List<Map<String, Object>> getPrescription(String chilCode) {
        return tChildInoculateDao.selectCurrentRegisterRecord(chilCode);
    }

    @Override
    public List<Map<String, Object>> getCurrentRegisterVacc(String chilCode) {
        return tChildInoculateDao.queryCurrentDayWaitInocBioName(chilCode);
    }

    @Override
    public List<Map<String, Object>> getCurrentInoculateVacc(String chilCode) {
        return tChildInoculateDao.queryCurrentDayInoculation(chilCode);
    }

    @Override
    public int saveAsBackUp(TChildInoculateEntity tChildInoculate) {
        return tChildInoculateDao.saveAsBackUp(tChildInoculate);
    }

    @Override
    public List<Map<String, Object>> queryUploadRecord(Map<String, Object> map) {
        return tChildInoculateDao.uploadRecord(map);
    }

    @Override
    public int queryTotalUploadRecord(Map<String, Object> map) {
        return tChildInoculateDao.uploadtotalRecord(map);
    }

    @Override
    public List<TChildInoculateEntity> suminoculateall() {
        return tChildInoculateDao.suminoculateall();
    }

    @Override
    public List<TChildInoculateEntity> inoculatelists(String orgid) {
//        String org_id = ShiroUtils.getUserEntity().getOrgId();
        return tChildInoculateDao.inoculatelists(orgid);
    }

    @Override
    public List<TChildInoculateEntity> observelist() {
        return tChildInoculateDao.observelist(Constant.GLOBAL_ORG_ID);
    }
    @Override
    public List<TChildInoculateEntity> querylistjzbl(Map<String,Object> map) {
        return tChildInoculateDao.querylistjzbl(map);
    }

    @Override
    public List<TChildInoculateEntity> noobservelist() {
        return tChildInoculateDao.noobservelist();
    }
    @Override
    public int observeupdate(TChildInoculateEntity tChildInoculateEntity) {
        return   tChildInoculateDao.observeupdate(tChildInoculateEntity);
    }
    @Override
    public List<TVacInfoEntity> outsideinoculatebio() {
        return   tChildInoculateDao.outsideinoculatebio();
    }
    @Override
    public List<TVacInfoEntity> getBioClassIdType(String bioClassId) {
        return   tChildInoculateDao.getBioClassIdType(bioClassId);
    }

    @Override
    public int queryUpdateInoculateNumByMap(Map<String, Object> map) {
        return tChildInoculateDao.queryUpdateInoculateNumByMap(map);
    }

    @Override
    public List<TChildInoculateEntity> queryUpdateInoculateDate(Map<String, Object> map) {
        return tChildInoculateDao.queryUpdateInoculateDate(map);
    }

}
