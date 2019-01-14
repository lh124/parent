package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildAllergyEntity;
import io.yfjz.entity.child.TChildHealthcareEntity;

import java.util.List;

/**
 * @author 刘琪
 * @class_name: TChildHealthcareDao
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-13 14:31
 */
public interface TChildHealthcareDao extends BaseDao<TChildHealthcareEntity> {

//    List<TChildHealthcareEntity> healthcarelist();

    /**
     * 儿保完成总数
     * @return
     */
    List<TChildHealthcareEntity> healthcarelists();
}
