package io.yfjz.service.bus.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.yfjz.dao.bus.ChildDao;
import io.yfjz.dao.child.*;
import io.yfjz.entity.child.*;
import io.yfjz.service.bus.ChildService;
import io.yfjz.utils.page.PageBean;
import io.yfjz.utils.page.PageParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 16:48 2018/09/29
 */
@Service("childService")
public class ChildServiceImpl implements ChildService {
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChildServiceImpl.class);
    @Autowired
    private ChildDao childDao;

    @Override
    public PageBean<List<Map<String, Object>>> listDataChild(TChildInfoEntity tChildInfoEntity, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNo() == null ? 1 : pageParam.getPageNo(), pageParam.getPageSize() == null ? 10 : pageParam.getPageSize());
        Page<List<TChildInfoEntity>> child = childDao.listDataChildList(tChildInfoEntity);
        return new PageBean(child);
    }

    @Override
    public Map<String, Object> getInoculateInfoByChildId(Map<String, Object> map) {
        Map<String, Object> retust = new HashMap<>();
        String firstChildCode = map.get("firstChildCode").toString();
        String secondChildCode = map.get("secondChildCode").toString();
        //第一个儿童的信息
        retust.put("firstChilHere", childDao.getChildInfoByChildId(firstChildCode));//根据儿童编号查询儿童信息
        retust.put("firstChilNeedle", childDao.getChildRecord(firstChildCode));//查询当前儿童的接种记录
        retust.put("firstChilDate", childDao.getLastInoculateDateByChildId(firstChildCode));//查询儿童最后一次接种时间
        //第二个儿童的信息
        retust.put("secondChilHere", childDao.getChildInfoByChildId(secondChildCode));
        retust.put("secondChilNeedle", childDao.getChildRecord(secondChildCode));
        retust.put("secondChilDate", childDao.getLastInoculateDateByChildId(secondChildCode));

        return retust;
    }

    @Override
    //@Transactional
    @Transactional(propagation= Propagation.REQUIRED)
    public Map<String, Object> margeInoculate(String param) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject json = JSONObject.fromObject(param);
            String savechildCode = json.getString("savechildCode");
            String repeatchildCode = json.getString("repeatchildCode");
            JSONArray mergeChil = json.getJSONArray("repeatinoculateId");
            JSONArray childNoMerge = json.getJSONArray("childNoMerge");
           List<String> saveChildId = new ArrayList<>();

            log.info("保留儿童编号：" + savechildCode + "被合并儿童编号：" + repeatchildCode);
            //遍历要合并的接种记录
            for(int i=0;i< mergeChil.size();i++){
               String repeatinoculateId = mergeChil.getString(i);
                log.info("根据勾选的接种记录编号：" + repeatinoculateId);
                String childId = childDao.queryChildId(savechildCode,repeatinoculateId);
                if(childId != null){
                    saveChildId.add(repeatinoculateId);
                    continue;
                }

                //根据勾选的id，查询出疫苗编号、剂次、接种属性
                Map<String, Object> queryRepeationInfoMap = childDao.queryRepeationInfoMap(repeatinoculateId);
                log.info("根据勾选的id，查询出疫苗编号，剂次和接种属性为：" + queryRepeationInfoMap);
                if(queryRepeationInfoMap != null){
                    //根据勾选的儿童的接种记录查询出来的接种疫苗、剂次、接种属性，然后查询需要保存的儿童的接种记录中是否存在统一疫苗，同一剂次的疫苗，如果存在，更新，不存在则新增
                    String inocTime = String.valueOf(queryRepeationInfoMap.get("inocTime"));
                    String inocProperty = String.valueOf(queryRepeationInfoMap.get("inocProperty"));
                    String inocBactCode = String.valueOf(queryRepeationInfoMap.get("inocBactCode"));
                    String dbMap = childDao.queryChildRecord(savechildCode, inocTime, inocProperty,inocBactCode);
                    log.info("根据勾选的儿童的接种记录查询出来的接种疫苗，剂次查询合并儿童有没有数据，如果有就修改为没有提交，没有修改被合并儿童接种记录的儿童编号。查询出来的数据为：" + dbMap);
                    if (dbMap != null) {
                        log.info("保留儿童（儿童编号："+ savechildCode +"）有相同接种记录,先伪删除保留儿童的相接种记录，在把不保留儿童的接种记录添加到保证儿童接种记录里");
                        childDao.updateChildRecord("1", dbMap);
                        TChildInoculateEntity tchild = childDao.queryChildInoculate(repeatinoculateId);
                        log.info("查询出合并的接种记录："+tchild);
                        tchild.setSyncstatus(0);
                        tchild.setChilCode(savechildCode);
                        childDao.addChildInoculate(tchild);
                        log.info("把不保留儿童接种记录勾选合并的接种记录添加到保留儿童的接种记录成功！");
                    } else {
                        log.info("合并的接种记录没有相同的接种记录");
                        TChildInoculateEntity tchild = childDao.queryChildInoculate(repeatinoculateId);
                        log.info("查询出合并的接种记录："+tchild);
                        tchild.setSyncstatus(0);
                        tchild.setChilCode(savechildCode);
                        childDao.addChildInoculate(tchild);
                        log.info("把不保留儿童接种记录勾选合并的接种记录添加到保留儿童的接种记录成功！");
                    }

                }
            }

            List<TChildInfectionEntity> TchildInfection = childDao.TchildInfection(repeatchildCode);
            log.info("开始合并儿童传染病信息：" +TchildInfection);
            for(TChildInfectionEntity fection : TchildInfection){
                fection.setChilCode(savechildCode);
                tChildInfectionDao.save(fection);
            }
            //childDao.updateTchildInfection(savechildCode,repeatchildCode);
            log.info("结束合并儿童传染病信息");

            List<TChildAllergyEntity> TchildAllergy = childDao.TchildAllergy(repeatchildCode);
            log.info("开始合并儿童过敏表：" + TchildAllergy);
            for(TChildAllergyEntity allergy : TchildAllergy){
                allergy.setChilCode(savechildCode);
                tChildAllergyDao.save(allergy);
            }
           // childDao.updateTchildAllergy(savechildCode,repeatchildCode);
            log.info("结束合并儿童过敏表");

            List<TChildAbnormalEntity> TchildAbnormal = childDao.TchildAbnormal(repeatchildCode);
            log.info("开始合并儿童异常反应表：" + TchildAbnormal);
            for(TChildAbnormalEntity abnormal : TchildAbnormal){
                abnormal.setChilCode(savechildCode);
                tChildAbnormalDao.save(abnormal);
            }
            //childDao.updateTchildAbnormal(savechildCode,repeatchildCode);
            log.info("结束合并儿童异常反应表");

            List<TChildTabooEntity> TchildTaboo = childDao.TchildTaboo(repeatchildCode);
            log.info("开始合并儿童禁忌表：" + TchildTaboo);
            for(TChildTabooEntity taboo : TchildTaboo){
                taboo.setChilCode(savechildCode);
                tChildTabooDao.save(taboo);
            }
            //childDao.updateTchildTaboo(savechildCode,repeatchildCode);
            log.info("结束合并儿童禁忌表");

            List<TChildMoveEntity> TchildMove = childDao.TchildMove(repeatchildCode);
            log.info("开始合并儿童迁移记录表：" + TchildTaboo);
            for(TChildMoveEntity move : TchildMove){
                move.setChilCode(savechildCode);
                tChildMoveDao.save(move);
            }
           // childDao.updateTchildMove(savechildCode,repeatchildCode);
            log.info("结束合并儿童迁移记录表");

            //伪删除数据库中的儿童信息
            if (StringUtils.isNotEmpty(savechildCode)) {
                childDao.updateChildInfoRecord("1", repeatchildCode);
                log.info("伪删除数据库中的不保留儿童信息 --儿童编号为：" + repeatchildCode);
                //childDao.updateInoculateChildCode("1", repeatchildCode);
                //log.info("伪删除数据库中的不保留儿童接种记录 ----儿童编号为：" + repeatchildCode);
            }


            //遍历伪删除保留儿童不合并的接种记录
            for(int i=0;i<childNoMerge.size();i++){
                String childNoMergeId = childNoMerge.getString(i);
                childDao.updateChildRecord("1",childNoMergeId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //报错之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            map.put("success", "0");
            map.put("msg", "合并失败");
            return map;
        }
        map.put("success", "1");
        map.put("msg", "合并成功");
        return map;
    }
    @Autowired
    private TChildInfectionDao tChildInfectionDao;
    @Autowired
    private TChildAllergyDao tChildAllergyDao;
    @Autowired
    private TChildAbnormalDao tChildAbnormalDao;
    @Autowired
    private TChildTabooDao tChildTabooDao;
    @Autowired
    private TChildMoveDao tChildMoveDao;
}
