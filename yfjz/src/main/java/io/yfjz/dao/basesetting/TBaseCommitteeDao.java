package io.yfjz.dao.basesetting;

import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 居委会表(居委会/行政村)
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-28 10:45:07
 */
public interface TBaseCommitteeDao extends BaseDao<TBaseCommitteeEntity> {
    /**
     * 分页查询数据
     *
     * @param name
     * @param orgId
     * @return
     */
    Page<List<TBaseCommitteeEntity>> queryListPage(@Param("name") String name, @Param("orgId") String orgId);


    void saveAdministrative(TBaseCommitteeEntity committee);

    void updateAdministrative(TBaseCommitteeEntity committee);

    void deleteAdministrative(String[] id);
}
