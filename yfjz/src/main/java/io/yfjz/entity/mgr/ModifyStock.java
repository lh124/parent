package io.yfjz.entity.mgr;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * create by tianjinhai on 2018/11/5 9:49
 */
@Setter
@Getter
public class ModifyStock {

    /**
     * 修改者
     */
    private String createUser;
    /**
     * 修改时间
     */
    private Date createTime;
    /**
     * 产品基础ID
     */
    private String baseId;
    /**
     * 入库明细ID
     */
    private String itemId;
    /**
     * 原数量
     */
    private Long preAmount;
    /**
     * 修改后的数量
     */
    private Long nowAmount;
    /**
     * 备注
     */
    private String remark;
}
