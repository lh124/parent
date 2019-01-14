package io.yfjz.dao.basesetting;


import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import io.yfjz.entity.basesetting.TBaseHospitalEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 13:47 2018/07/28
 */

public interface TBaseHospitalDao extends BaseDao<TBaseHospitalEntity> {

    Page<List<TBaseHospitalEntity>> queryListPage(@Param("hospitalName") String hospitalName);


}
