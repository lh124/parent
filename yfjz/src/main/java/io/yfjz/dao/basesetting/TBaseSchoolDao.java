package io.yfjz.dao.basesetting;


import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseSchoolEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 入学入托
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 14:34 2018/07/27
 */

public interface TBaseSchoolDao extends BaseDao<TBaseSchoolEntity> {
    /**
     * 查询入学入托 自动分页
     * @return
     */
    Page<List<TBaseSchoolEntity>> queryListPage(@Param("name") String name,@Param("orgId") String orgId);
}
