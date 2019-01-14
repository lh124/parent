package io.yfjz.dao.bus;


import com.github.pagehelper.Page;
import io.yfjz.entity.child.*;
//import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 17:18 2018/09/29
 */

public interface ChildDao {

    /**
     * 根据条件查询重复儿童记录
     * @param child
     * @return
     */
    Page<List<TChildInfoEntity>> listDataChildList(TChildInfoEntity child);

    /**
     * 根据儿童编号查询儿童信息
     * @return
     */
    Map<String,Object> getChildInfoByChildId(@Param("chilCode") String chilCode);

    /**
     * 查询儿童的针次
     * @param chilCode
     * @return
     */
    Integer getInoculateInfoByChildId(@Param("chilCode")String chilCode);

    /**
     * 查询当前儿童的接种记录
     * @param chilCode
     * @return
     */
    List<Map<String,Object>> getChildRecord(@Param("chilCode")String chilCode);

    /**
     * 查询儿童最后一次接种时间
     * @param chilCode
     * @return
     */
    String getLastInoculateDateByChildId(@Param("chilCode")String chilCode);

    /**
     * 根据儿童编号和接种记录编号查询当前这个接种记录编号是不是保存儿童的这个接种记录编号
     * @param chilCode
     * @param id
     * @return
     */
    String queryChildId(@Param("chilCode")String chilCode,@Param("id") String id);

    /**
     * 根据勾选的id，查询出疫苗名称，剂次，接种属性
     * @param id
     * @return
     */
    Map<String,Object> queryRepeationInfoMap(String id);

    /**
     * 根据勾选的儿童的接种记录查询出来的接种疫苗，剂次、接种属性，
     * 然后查询需要保存的儿童的节中记录中是否存在统一疫苗，同一剂次的疫苗，如果存在，更新，不存在则新增
     * @param chilCode 儿童编号
     * @param inocTime 剂次
     * @param inocProperty 接种属性
     * @param inocBactCode 疫苗编号
     * @return
     */
    String queryChildRecord(@Param("chilCode") String chilCode,@Param("inocTime")String inocTime,@Param("inocProperty")String inocProperty,@Param("inocBactCode") String inocBactCode);

    /**
     * 伪删除
     * @param delete
     * @param id
     * @return
     */
    Integer updateChildRecord(@Param("delete") String delete,@Param("id") String id);

    /**
     * 根据儿童编号伪删除接种记录
     * @param delete
     * @param chilCode
     * @return
     */
    Integer updateInoculateChildCode(@Param("delete") String delete,@Param("chilCode") String chilCode);

    /**
     * 更新本条记录的儿童编码为保留的儿童的编码
     * @return
     */
    Integer  updateChildRecordCode(@Param("chilCode")String chilCode,@Param("id") String id);

    /**
     *根据接种编号查询这条接种记录
     * @param id
     * @return
     */
    TChildInoculateEntity queryChildInoculate(String id);

    /**
     * 根据儿童编号添加接种记录
     * @param tchild
     * @return
     */
    Integer addChildInoculate(TChildInoculateEntity tchild);

    /**
     * 伪删除儿童信息记录
     * @param delete
     * @param chilCode
     * @return
     */
    Integer updateChildInfoRecord(@Param("delete") String delete,@Param("chilCode") String chilCode);

    /**
     * 根据儿童编号查询儿童迁移记录表
     * @param chilCode
     * @return
     */
    List<TChildMoveEntity> TchildMove(String chilCode);

    /**
     * 根据儿童编号修改儿童编号合并用
     * @param code 添加的儿童编号
     * @param chilCode 被修改的儿童编号
     * @return
     */
    Integer updateTchildMove(@Param("code")String code,@Param("chilCode")String chilCode);

    /**
     * 根据儿童编号查询儿童禁忌表
     * @param chilCode
     * @return
     */
    List<TChildTabooEntity> TchildTaboo(String chilCode);

    /**
     * 根据儿童编号修改儿童编号合并用
     * @param code 添加的儿童编号
     * @param chilCode 被修改的儿童编号
     * @return
     */
    Integer updateTchildTaboo(@Param("code")String code,@Param("chilCode")String chilCode);

    /**
     * 根据儿童编号查询儿童异常反应表
     * @param chilCode
     * @return
     */
    List<TChildAbnormalEntity> TchildAbnormal(String chilCode);

    /**
     * 根据儿童编号修改儿童编号合并用
     * @param code 添加的儿童编号
     * @param chilCode 被修改的儿童编号
     * @return
     */
    Integer updateTchildAbnormal(@Param("code")String code,@Param("chilCode")String chilCode);

    /**
     * 根据儿童编号查询儿童过敏表
     * @param chilCode
     * @return
     */
    List<TChildAllergyEntity> TchildAllergy(String chilCode);

    /**
     * 根据儿童编号修改儿童编号合并用
     * @param code 添加的儿童编号
     * @param chilCode 被修改的儿童编号
     * @return
     */
    Integer updateTchildAllergy(@Param("code")String code,@Param("chilCode")String chilCode);

    /**
     *根据儿童编号查询儿童传染病表
     * @param chilCode
     * @return
     */
    List<TChildInfectionEntity> TchildInfection(String chilCode);

    /**
     * 根据儿童编号修改儿童编号合并用
     * @param code 添加的儿童编号
     * @param chilCode 被修改的儿童编号
     * @return
     */
    Integer updateTchildInfection(@Param("code")String code,@Param("chilCode")String chilCode);

}
