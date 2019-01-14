package io.yfjz.dao.webService;


import io.yfjz.entity.sys.SysDepartEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 15:11 2018/09/12
 */

public interface DataSynchronizationDao {
    /**
     * 根据传入的表和表主键编号id查询是否有这条数据 返回一个Map数据
     * @return
     */
    List<Map<String,Object>> queryTableId(@Param("column") List<String> column, @Param("tableName") String tableName, @Param("condition") Map<String,Object> condition);

    /**
     * 根据传入的表和表主键编号id查询是否有这条数据 如果有返回数量
     * @return
     */
    Integer queryTableIdCount(@Param("tableName") String tableName, @Param("condition") Map<String,Object> condition);

    /**
     * 根据传入的表添加数据
     * @return
     */
    Integer addTableData(@Param("tableName") String tableName,@Param("data") Map<String,Object> data);

    /**
     * 根据传入的表和表的主键更新数据
     * @return
     */
    Integer updateTableData(@Param("tableName") String tableName,@Param("data") Map<String,Object> data,@Param("condition") Map<String,Object> condition);

    /**
     * 根据编号查询机构所有信息
     * @param orgId
     * @return
     */
    SysDepartEntity queryDepartList(String orgId);

    /**
     *  根据数据库和表名查询表的主键
     * @param table 表名
     * @param database 数据库名
     * @return
     */
    List<String> queryTableKey(@Param("table") String table,@Param("database") String database);

    /**
     * 删除指定表的表数据
     * @param condition
     * @return
     */
    Integer deleteTableData(@Param("tableName") String tableName,@Param("condition") Map<String,Object> condition);
}
