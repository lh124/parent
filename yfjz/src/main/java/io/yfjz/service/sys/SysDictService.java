//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.utils.R;

import java.util.List;
import java.util.Map;

public interface SysDictService {
    void save(SysDictEntity var1);

    void update(SysDictEntity var1);

    void updateValueByKey(String var1, String var2);

    void deleteBatch(String[] var1);

    List<SysDictEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    SysDictEntity queryObject(String var1);

    String getValue(String var1, String var2);
    /** 
    * @Description: 根据字典表类型查询数据 
    * @Param: [type] 
    * @return: java.util.List 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/7/27 10:07
    * @Tel  17328595627
    */ 
    List selectListByType(String type);

    /**
     * 批量启用记录
     * @param ids
     */
    void startUsingBatch(String[] ids);

    /**
     * 批量禁用记录
     * @param ids
     */
    void forbiddenBatch(String[] ids);


    /**
     * @method_name: queryMapList
     * @describe: 接种界面初始化公共数据，缓存到界面vue对象
     * 说明：只加载接种部位，接种属性 类型ttype
     * @param: []
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/3  10:58
     **/
    R queryMapList();

    /**
     *省平台下载儿童一次性加载 户籍属性、个案状态和居住属性
     * @param ttype 如果ttype值不为空，根据ttype来查询
     * @author: 张羽丰
     * @date: 2018/10/18 15:18
     * @return
     */
    R queryResultTable(String ttype);

}
