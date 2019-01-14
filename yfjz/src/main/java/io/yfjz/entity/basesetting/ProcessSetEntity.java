package io.yfjz.entity.basesetting;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * create by tianjinhai on 2018/9/13 10:26
 */
@Setter
@Getter
public class ProcessSetEntity {

    //主键 guid
    private String id;
    //工作台名称
    private String towerName;
    //工作台类型Id
    private Integer towerNatureId;
    //流程序号
    private Integer sequence;
    ///创建时间
    private Date createTime;
    private Integer  status;
    //机构ID
    private String orgId;
    //工作台英文缩写
    private String towerEnName;

}
