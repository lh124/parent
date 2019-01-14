package io.yfjz.entity.sys;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 系统相关功能是否启用配置表
 * @Date 16:48 2018/10/19
 */

@Getter
@Setter
@ToString
public class SysConfigurationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String type;//类型
    private Integer startUsing;//查看这个功能是否启用：0启用，1禁用这个功能
    private String remark;//备注
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String creator;//创建者
    private String modifier;//修改者
    private Integer state;//状态  0:禁用1：正常
}
