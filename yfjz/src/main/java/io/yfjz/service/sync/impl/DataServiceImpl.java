package io.yfjz.service.sync.impl;

import io.yfjz.dao.basesetting.TBaseCommitteeDao;
import io.yfjz.dao.basesetting.TBaseSchoolDao;
import io.yfjz.dao.sync.DataDao;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.sync.DataService;
import io.yfjz.service.webService.MeterFreeUploadPlatformService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: ImportDataServiceImpl
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-03 16:57
 */

@Service
public class DataServiceImpl implements DataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataDao dataDao;

    @Autowired
    TChildInfoService tChildInfoService;

    @Autowired
    TChildInoculateService tChildInoculateService;

    @Autowired
    TBaseCommitteeDao tBaseCommitteeDao;

    @Autowired
    TBaseSchoolDao tBaseSchoolDao;

    @Autowired
    private MeterFreeUploadPlatformService meterFreeUploadPlatformService;


    @Override
    public R childUpdate() {
        try {
            if (StringUtils.isEmpty(Constant.GLOBAL_ORG_ID)){
                return R.error("参数为空，请登录！");
            }
            if (Constant.GLOBAL_ORG_ID.length()!=10){
                return R.error("当前登录的账户不是机构管理员，请切换机构管理员用户");
            }

            //判断儿童基本表中是否已经有数据，如果有数据，则不能执行同步功能
            int total = tChildInfoService.queryAllTotal();
            if (total >0){
                return R.error("当前儿童信息表中已经有数据，不能重复同步数据，重复导入数据会导致数据出错");
            }

            try {
                dataDao.renameBaseTableToOperation();
            } catch (Exception e) {
                logger.info("调用renameBaseTableToOperation更新表名异常{}"+e.getMessage());
            }

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("orgId",Constant.GLOBAL_ORG_ID);
            dataDao.synchronizedChildFrom(paramMap);
            return R.ok("同步儿童基本信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统错误，请联系管理员！" + e.getMessage());
        }
    }

    @Override
    public R synchronizedChildInoculations() {
        try {

            //判断儿童基本表中是否已经有数据，如果有数据，则不能执行同步功能
            int total = tChildInoculateService.queryAllTotal();
            if (total >0){
                return R.error("当前儿童接种表中已经有数据，不能重复同步数据，重复导入数据会导致数据出错");
            }

            dataDao.synchronizedChildInoculations();
            return R.ok("同步儿童历史接种记录成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统异常，请联系管理员"+e.getMessage());
        }
    }

    @Override
    public R syncData(String orgId) {
        try {
            if (StringUtils.isEmpty(Constant.GLOBAL_ORG_ID)){
                return R.error("参数为空，请登录！");
            }
            if (Constant.GLOBAL_ORG_ID.length()!=10){
                return R.error("当前登录的账户不是机构管理员，请切换机构管理员用户");
            }
            //判断表有没有传输过来
            Integer numberChild = dataDao.isTableExist("child");
            if (numberChild == null || numberChild < 1){
                return R.error("金卫信的儿童表：Child 没有传输过来！");
            }
            Integer numbersyswhb = dataDao.isTableExist("syswhb");
            if (numbersyswhb == null || numbersyswhb < 1){
                return R.error("金卫信的儿童表：SYSWHB 没有传输过来！");
            }
            Integer numbernursery = dataDao.isTableExist("nursery");
            if (numbernursery == null || numbernursery < 1){
                return R.error("金卫信的儿童表：nursery 没有传输过来！");
            }
            Integer numberQyhf = dataDao.isTableExist("qyhf");
            if (numberQyhf == null || numberQyhf < 1){
                return R.error("金卫信的儿童表：Qyhf 没有传输过来！");
            }

            //判断儿童基本表中是否已经有数据，如果有数据，则不能执行同步功能
            int total = tChildInfoService.queryAllTotal();
            if (total >0){
                return R.error("当前儿童信息表中已经有数据，不能重复同步数据，重复导入数据会导致数据出错");
            }
            //行政村数据检查
            int commtotal = tBaseCommitteeDao.queryTotal();
            if (commtotal >0){
                return R.error("行政村已有数据，请检查");
            }
            //学校数据检查
            int schooltotal = tBaseSchoolDao.queryTotal();
            if (schooltotal >0){
                return R.error("学校已有数据，请检查");
            }

            try {
                dataDao.syncData(orgId);

                Map<String,Object> upload = meterFreeUploadPlatformService.updatePlatform();
                //判断上传行政村和学校的数据是否成功
                if(upload.get("code")!=null&&"0".equals(upload.get("code").toString())){
                    return R.ok("同步儿童基本信息成功，上传行政村和学校的数据成功！");
                }else{
                    return R.ok("同步儿童基本信息成功，上传行政村和学校的数据失败！可以到数据同步重新上传。");
                }
                //dataDao.renameBaseTableToOperation();
            } catch (Exception e) {
                e.printStackTrace();
                return R.error("数据库执行同步操作错误，请检查您的操作步骤!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("系统错误，请联系管理员！" + e.getMessage());
        }
    }
}
