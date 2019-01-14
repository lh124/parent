package io.yfjz.service.bus;

import io.yfjz.utils.R;

import java.util.List;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: VaccRegisterService
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-17 11:17
 */
public interface VaccRegisterService {
    /**
     * @method_name: addRecommendList
     * @describe: 登记疫苗 可选疫苗 -- 存到推荐疫苗
     * @param: [listMap]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/17  11:20
     **/
    R addRecommendList(List<Map> listMap);


    /**
     * @method_name: removeAddRegister
     * @describe: 点击删除选择的列表  弹框中的推荐疫苗列表
     * @param: [id]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/17  17:42
     **/
    R removeAddRegister(String id);


    /**
     * @method_name: countByChildCodeAndVaccCode
     * @describe: 根据儿童编码，疫苗编码统计该儿童该疫苗所属大类下的剂次，历史接种记录表中查询
     * @param: [paramMap]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/21  9:27
     **/
    R countByChildCodeAndVaccCode(Map paramMap);


    /**
     * @method_name: exist
     * @describe: 判断当天某儿童是否登记过同类疫苗
     * @param: [paramMap]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/22  16:23
     **/
    R exist(Map paramMap);



    /**
     * @method_name: deleteAll
     * @describe: 删除某儿童今日已登记的所有疫苗信息，登记表
     * @param: [childCode]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/23  10:39
     **/
    R deleteAll(String childCode);
}
