package io.yfjz.dao.sync;

import java.util.Map;

/**
 * @author 刘琪
 * @class_name: Datadao
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-03 17:10
 */
public interface DataDao {

    void renameBaseTableToOperation();

    /**
     * @method_name: synchronizedChildFrom
     * @describe: 同步儿童基本信息
     * @param: [paramMap]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/4  14:59
     **/
    void synchronizedChildFrom(Map paramMap);

    /**
     * @method_name: synchronizedChildInoculations
     * @describe: 同步儿童历史接种记录
     * @param: []
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/4  14:59
     **/
    void synchronizedChildInoculations();


    /**
     * 根据表明判断数据库是否存在该表，如果返回值大于0时，数据库存在该表。
     * @describe:
     * @param tableName
     * @return int
     * @author 邓召仕
     * @date: 2018-10-20  10:40
     **/
    int isTableExist(String tableName);

/**
 * 数据同步（同步金卫信原始数据）
 * @describe:
 * @param orgId
 * @return void
 * @author 邓召仕
 * @date: 2018-10-20  11:35
 **/
    void syncData(String orgId);
}
