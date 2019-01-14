package io.yfjz.dao.basesetting;


import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TVacClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 16:24 2018/07/31
 */

public interface TVacClassDao extends BaseDao<TVacClassEntity> {
    /**
     * 分页查询数据
     * @param name
     * @return
     */
    Page<List<TVacClassEntity>> queryListPage(@Param("name") String name);


    List<TVacClassEntity> queryAllVaccClassList();

    Integer startVacClass(@Param("id") String[] id,@Param("status") Integer status);

}
