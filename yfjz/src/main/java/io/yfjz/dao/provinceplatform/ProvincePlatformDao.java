package io.yfjz.dao.provinceplatform;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.provinceplatform.*;

import java.util.List;
import java.util.Map;

/**
 * 儿童基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
public interface ProvincePlatformDao extends BaseDao<Child> {
    int saveChild(Child val);
    int updateChild(Child val);
    int saveAllergy(Child val);
    int findAllergyByCondition (Child val);
    int findInoculateByCondition (Inoculation val);
    int saveLocalInoculations (Inoculation val);
    int updateLocalInoculations (Inoculation val);
    int findMoveByCondition (ChildHere val);
    int saveMove (ChildHere val);
    int findInfectionByCondition (Infection val);
    int saveInfection (Infection val);
    int findAbnormalByCondition (Aefi val);
    int saveAbnormal(Aefi val);
    int findTabooByCondition (Istabu val);
    int saveTaboo(Istabu val);
    int saveMigrations(Map<String,Object> val);
    int queryMigrations(Map<String,Object> val);
    List<Map<String,Object>> selectByCurrentAddress (Map<String,Object> val);

    int updateSyncstatus(String chilCode);
	
}
