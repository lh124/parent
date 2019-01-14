//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
public class ScheduleJobLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long logId;
    private Long jobId;
    private String beanName;
    private String methodName;
    private String params;
    private Integer status;
    private String error;
    private Integer times;
    private Date createTime;

}
