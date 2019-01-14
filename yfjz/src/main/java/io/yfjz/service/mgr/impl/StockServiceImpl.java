package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrStockBaseDao;
import io.yfjz.service.mgr.StockService;
import io.yfjz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * create by tianjinhai on 2018/8/14 15:21
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private TMgrStockBaseDao baseDao;

    @Override
    public Map<String, Object> judgeStock(List<String> codes) {
        //查出每个疫苗最优的推荐
        List<Map<String, Object>> list = new ArrayList<>();
        for (String code : codes) {
            Map<String,Object> temp=baseDao.queryListByCodeId(code);
            if(temp!=null){
                list.add(temp);
            }
        }
        //全部没有库存，返回空
        if (list.size()<=0){
            return  null;
        }
        //从每一个疫苗的最优推荐中选择最优推荐
        List<Map<String, Object>> priceList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            BigDecimal price = new BigDecimal(map.get("price").toString());
            //如果为免费的疫苗加入新的集合
            if (price.compareTo(BigDecimal.ZERO)==0){
                priceList.add(map);
            }
        }
        //判断免费的疫苗集合是否为null
        if (priceList.size()>0) {
            //按照时间排序取出第一条返回
            Collections.sort(priceList, new Comparator<Map<String, Object>>(){
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Date date1 =(Date)o1.get("expiryDate");
                    Date date2= (Date)o2.get("expiryDate");
                    return   DateUtils.dateCompare(date1,date2);
                }
            });
            return priceList.get(0);
        }else{
            //没有免费的疫苗，取出收费疫苗，失效期最短的一条
            Collections.sort(list, new Comparator<Map<String, Object>>(){
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Date date1 =(Date)o1.get("expiryDate");
                    Date date2= (Date)o2.get("expiryDate");
                    return   DateUtils.dateCompare(date1,date2);
                }
            });
            return list.get(0);
        }
    }
}
