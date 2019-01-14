package io.yfjz.dao.basesetting;

import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工作台基本信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-24 11:01:00
 */
public interface TBaseTowerDao extends BaseDao<TBaseTowerEntity> {
    // Page<List<BaseTowerEntity>> queryListPage(Map<String, Object> var1);

    Page<List<TBaseTowerEntity>> queryListPage(@Param("towerName") String towerName,@Param("orgId") String orgId);

    /**
     * 根据组织机构查询orgid
     * @param orgId
     * @return
     */
    List<TBaseTowerEntity> queryListByOrgId(String orgId);


    /**
     * @method_name: queryTotalByTowerNameAndOrgId
     * @describe: 根据工作台名称，机构名称查询
     * @param: [hashMap]
     * @return: int
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/13  9:11
     **/
    int queryTotalByTowerNameAndOrgId(HashMap<String, Object> hashMap);
    /** 
    * @Description: 查询所有未绑定仓库的接种台
    * @Param: [globalOrgId]
    * @return: java.util.List<io.yfjz.entity.basesetting.TBaseTowerEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 9:22
    * @Tel  17328595627
    */
    List<TBaseTowerEntity> queryListInoculateByOrgId(String ogrId);
    
    /** 
    * @Description: 查询所有的接种台
    * @Param: [] 
    * @return: java.util.List<java.lang.String> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 13:50
    * @Tel  17328595627
    */ 
    List<String> getAllTowers();

    
    /**
     * @method_name: getTowerListByTowerIds
     * @describe: 根据当前机构，当前剩余能选择的工作台ids列表查询工作台信息
     * @param: [paramMap]
     * @return: java.util.List<io.yfjz.entity.basesetting.TBaseTowerEntity>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/28  19:38
     **/
    List<TBaseTowerEntity> getTowerListByTowerIds(HashMap paramMap);
    
    /**
     * @method_name: getTowerListByMap
     * @describe: 根据orgId,工作台类型，工作台集合获取可选工作台
     * @param: [param]
     * @return: java.util.List<java.util.Map> 
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/29  18:04
     **/
    List<Map> getTowerListByMap(HashMap param);

    List<TBaseTowerEntity> getTowerListByNotIds(HashMap param);
    /** 
    * @Description: 查询疫苗所在的主仓库 
    * @Param: [fkBaseInfo] 
    * @return: java.lang.String 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/30 20:44
    * @Tel  17328595627
    */
    List<Map> queryStoreIdByCode(String fkBaseInfo);

    List<TBaseTowerEntity> queryTowerOnline(@Param("towers") List<String> towers, @Param("type") Integer type);
}
