//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysDictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysDictDao extends BaseDao<SysDictEntity> {
    String queryByKey(String var1);

    int updateValueByKey(@Param("key") String var1, @Param("value") String var2);

    int startUsingBatch(String[] var1);

    int forbiddenBatch(String[] var1);
    List<SysDictEntity> selectListByType(String type);

    List<SysDictEntity> queryListName(Map<String,Object> map);


    /**
     * @method_name: queryMapList
     * @describe: 接种界面初始化公共数据，缓存到界面vue对象
     * 说明：只加载接种部位，接种属性 类型ttype
     * @param: [list]
     * @return: java.util.List<java.util.Map>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/3  11:01
     **/
    List<SysDictEntity> queryMapList(HashMap param);

    String[] queryInoculateSiteList();

    String queryValueByText(Map<String, Object> queryTemp);
}
