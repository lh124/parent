package io.yfjz.service.sync;

import io.yfjz.utils.R;

/**
 * @author 刘琪
 * @class_name: ImportDataService
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-03 16:57
 */
public interface DataService {

    /**
     * @method_name: childUpdate
     * @describe: 同步儿童基本信息
     * @param: []
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/6  9:11
     **/
    R childUpdate();


    /**
     * @method_name: synchronizedChildInoculations
     * @describe: 同步儿童历史接种记录
     * @param: []
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/6  9:11
     **/
    R synchronizedChildInoculations();

    /**
     * 新版同步金卫信数据
     * @describe:
     * @param orgId
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-10-20  11:23
     **/
    R syncData(String orgId);
}
