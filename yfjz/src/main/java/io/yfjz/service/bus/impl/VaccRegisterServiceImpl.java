package io.yfjz.service.bus.impl;

import io.yfjz.dao.bus.TBusRegisterDao;
import io.yfjz.dao.child.TChildInoculateDao;
import io.yfjz.dao.queue.TQueueNoDao;
import io.yfjz.entity.bus.TBusRegisterEntity;
import io.yfjz.entity.queue.QueueAction;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.service.bus.VaccRegisterService;
import io.yfjz.service.inocpointmgr.TBaseGetnumsService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 刘琪
 * @class_name: VaccRegisterServiceImpl
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-17 11:18
 */
@Service
public class VaccRegisterServiceImpl implements VaccRegisterService {


    @Autowired
    TBusRegisterDao tBusRegisterDao;

    @Autowired
    TChildInoculateDao tChildInoculateDao;

    @Autowired
    TQueueNoService tQueueNoService;

    @Autowired
    TBaseTowerService tBaseTowerService;

    //接种点设置
    @Autowired
    TBaseGetnumsService tBaseGetnumsService;
    @Autowired
    private TQueueNoDao tQueueNoDao;



    @Override
    public R addRecommendList(List<Map> listMap) {
        try {
            //邓：判断是否启用多队列
            R rerult = tBaseGetnumsService.getSysConType(Constant.QUEUE_DISTINCTION,0);
            if (rerult != null && ((Integer)rerult.get("typeValue")).intValue() ==1) {
                //邓召仕添加：判断可接种该疫苗的接种台是否已经登录
                List<String> bioCodes = new ArrayList<>();
                for (Map biomap : listMap) {
                    bioCodes.add(biomap.get("bioCode").toString());
                }
                R positionMap = tBaseTowerService.synergicPosition(bioCodes);
                if ((int) positionMap.get("code") == 0) {
                    return R.error(positionMap.get("msg").toString());
                }
            }
            //邓：ting
            //优先删除今日已登记的疫苗，然后再保存登记界面的推荐列表
            tBusRegisterDao.deleteAll(listMap.get(0).get("childCode").toString());
            for (Map map:listMap) {
                //保存登记选中的疫苗到登记列表
                //根据疫苗编码在接种信息表中查询该儿童接种了多少剂次，然后累加一剂次
                int dbCount = tChildInoculateDao.queryTotalByChildCodeAndVaccCode(map.get("childCode").toString(),map.get("bioCode").toString());
                TBusRegisterEntity bus = new TBusRegisterEntity();
                bus.setFkChildCode(map.get("childCode").toString());
                bus.setFkOrgId(Constant.GLOBAL_ORG_ID);
                bus.setFkVaccineCode(map.get("bioCode").toString());
                bus.setDoseNo(dbCount+1);
                bus.setBatchnum(map.get("batchnum").toString());
                try {
                    bus.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse(map.get("expiryDate").toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RRException("失效时间转换异常");
                }
                bus.setFactoryCode(map.get("factoryCode").toString());
                bus.setCreateDoctorId(ShiroUtils.getUserEntity().getUserId());
                bus.setCreateDoctorName(ShiroUtils.getUserEntity().getRealName());
                bus.setCreateTime(new Date());
                bus.setFkPosId(ShiroUtils.getUserEntity().getRegisterTowerId());
                bus.setIsmf(Integer.parseInt(map.get("ismf").toString()));
                bus.setStatus(0);//默认登记状态，未完成注射
                bus.setFkPosId(ShiroUtils.getUserEntity().getRegisterTowerId());
                bus.setInoculateSite(map.get("inoculateSite").toString());
                bus.setInoculateSiteCode(Integer.parseInt(map.get("inoculateSiteCode").toString()));
                tBusRegisterDao.save(bus);
            }
            //邓：是否启用排队叫号
            String sequenceNoId = null;
            if (listMap.get(0).get("sequenceNoId") !=null && !"".equals(listMap.get(0).get("sequenceNoId"))){//前台携带号码过来
                sequenceNoId = listMap.get(0).get("sequenceNoId").toString();
            }else {//没有启用排队叫号，所以前端未携带过来
                TQueueNoEntity tQueueNo = new TQueueNoEntity();
                Integer count = tQueueNoDao.queryTodayTotal();
                tQueueNo.setCreateTime(new Date());
                tQueueNo.setNo(count+1);
                tQueueNo.setAction(QueueAction.FINISH);
                tQueueNo.setType(0);
                tQueueNo.setStatus(0);
                tQueueNo.setStep("register");
                tQueueNoService.save(tQueueNo);
                sequenceNoId = tQueueNo.getId();
            }
            //邓：end

            //调用队列通知接口
            tQueueNoService.finishedCurrentStep("register",sequenceNoId,listMap.get(0).get("childCode").toString(),listMap.get(0).get("childName").toString());
            return R.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统异常,请联系管理员{}"+e.getMessage());
        }
    }

    @Override
    public R removeAddRegister(String id) {
        try {
            tBusRegisterDao.delete(id);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除异常");
        }
    }

    @Override
    public R countByChildCodeAndVaccCode(Map paramMap) {
        try {
            //校验参数
            if (StringUtils.isEmpty(String.valueOf(paramMap.get("childCode"))) || StringUtils.isEmpty(String.valueOf(paramMap.get("vaccineCode")))){
                throw new RRException("参数为空");
            }
            //校验当前登记所在时间，同类型登记疫苗是否存在，同种类型疫苗不能同时接种两剂次
            int dbCount = tChildInoculateDao.queryTotalByChildCodeAndVaccCode(paramMap.get("childCode").toString(),paramMap.get("vaccineCode").toString());
            return R.ok().put("total",dbCount+1);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询异常"+e.getMessage());
        }
    }

    @Override
    public R exist(Map paramMap) {
        //校验当前登记所在时间，同类型登记疫苗是否存在，同种类型疫苗不能同时接种两剂次
        int count = tBusRegisterDao.exist(new HashMap<String,Object>(){{
            put("childCode",paramMap.get("childCode").toString());
            put("vaccineCode",paramMap.get("vaccineCode").toString());
        }});
        if (count>0){
            throw new RRException("当日已登记过疫苗编码为（"+paramMap.get("vaccineCode").toString()+"）的同类疫苗，不能重复登记");
        }
        return R.ok("今日没有登记同类的疫苗，可以登记");
    }

    @Override
    public R deleteAll(String childCode) {
        try {
            //优先删除今日已登记的疫苗，然后再保存登记界面的推荐列表
            tBusRegisterDao.deleteAll(childCode);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除异常，请联系管理员");
        }
    }
}
