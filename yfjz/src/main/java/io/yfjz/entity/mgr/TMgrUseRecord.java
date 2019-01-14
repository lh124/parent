package io.yfjz.entity.mgr;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 疫苗使用记录
 * create by tianjinhai on 2018-12-24 11:09
 */
@Setter
@Getter
public class TMgrUseRecord {

    private String id;
    /**
     * 使用疫苗的工作台Id
     */
    private String nowTowerId;
    /**
     * 领取疫苗的仓库Id
     */
    private String receiveStoreId;
    /**
     * 产品基础信息Id
     */
    private String baseInfoId;
    /**
     * 使用人份数
     */
    private Long usePersonAmount;
    /**
     * 使用人
     */
    private String createUserId;
    /**
     * 使用时间
     */
    private Date createTime;
    /**
     * 删除状态
     */
    private int deleteStatus;
}
