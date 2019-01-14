package io.yfjz.service.mgr;

import java.util.List;
import java.util.Map;

/**
 * create by tianjinhai on 2018/8/14 15:03
 */
public interface StockService {
    /** 
    * @Description: 推荐最优的疫苗库存，优先推荐免费疫苗，然后推荐失效期端的疫苗，最后推荐收费，失效期短的疫苗 
    * @Param: [code,butchnum] 疫苗id，对应的批号 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 15:26
    * @Tel  17328595627
    */ 
    Map<String,Object> judgeStock(List<String> codes);
}
