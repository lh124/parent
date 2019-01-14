package io.yfjz.managerservice.statistics.impl;

import io.yfjz.dao.statistics.CardStatisticsDao;
import io.yfjz.entity.statistics.CreateCardRate;
import io.yfjz.managerservice.statistics.CardStatisticsSerivce;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 建卡率统计服务
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-27  15:12
 **/
@Service
public class CardStatisticsSerivceImpl implements CardStatisticsSerivce {
    @Autowired
    private CardStatisticsDao cardStatisticsDao;

    @Override
    public List<CreateCardRate> getCreateCardRate(Date createDateStart, Date createDateEnd, String[] chilCommittees, String[] infostatus,Date birthDayStart,Date birthDayEnd) {
        Map<String,Object> param = new HashMap<>();
        if (createDateStart != null){
            param.put("createDateStart",createDateStart);
        }
        if (createDateEnd != null){
            param.put("createDateEnd",createDateEnd);
        }
        if (birthDayStart != null){
            param.put("birthDayStart",birthDayStart);
        }
        if (birthDayEnd != null){
            param.put("birthDayEnd",birthDayEnd);
        }
        if (chilCommittees != null){
            param.put("chilCommittees",chilCommittees);
        }
        if(infostatus != null){
            param.put("infostatus",infostatus);
        }
//        param.put("orgId",ShiroUtils.getUserEntity().getOrgId());
        List<CreateCardRate> cardRates = cardStatisticsDao.getRateChildByCondition(param);
        int childTotal = 0,createCardTotal = 0,timelyTotal = 0;
        if(null != cardRates){
            for(CreateCardRate card : cardRates){
                param.put("committeeId", card.getCommitteeId());
                String timelyNumber = cardStatisticsDao.getCreateCradTimelyNumber(param);
                card.setTimelyNumber(timelyNumber);
                card.setCreateCardRate(calculateRate(card.getCreateCardCount(),card.getChildCount()));
                card.setTimelyRate(calculateRate(card.getTimelyNumber(),card.getCreateCardCount()));
                try {
                    int childn = Integer.parseInt(card.getChildCount());
                    int createCardn = Integer.parseInt(card.getCreateCardCount());
                    int timelyn = Integer.parseInt(card.getTimelyNumber());
                    childTotal+=childn;
                    createCardTotal+=createCardn;
                    timelyTotal+=timelyn;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            CreateCardRate createTotal = new CreateCardRate();
            createTotal.setCommitteeName("合计");
            createTotal.setChildCount(childTotal+"");
            createTotal.setCreateCardCount(createCardTotal+"");
            createTotal.setTimelyNumber(timelyTotal+"");
            cardRates.add(createTotal);
        }
        return cardRates;
    }

    /**
     * 计算百分比
     * @作者：邓召仕
     * @编写时间：2018-5-27
     * @param dividend 被除数
     * @param divider 除数
     * @return
     */
    private String calculateRate(String dividend,String divider){
        String rate = "0.00%";
        if(!StringUtils.isEmpty(dividend)&& !StringUtils.isEmpty(divider)){
            try {

                Double dividendDb = Double.parseDouble(dividend);
                Double dividerDb = Double.parseDouble(divider);
                if(dividerDb==0){
                    rate="0.00%";
                }else{
                    Double rult = new BigDecimal(dividendDb*100/dividerDb).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    rate = String.valueOf(rult) + "%";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return rate;

    }
}
