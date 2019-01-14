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
public class ScheduleJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    private Long jobId;
    private String beanName;
    private String methodName;
    private String params;
    private String cronExpression;
    private Integer status;
    private String remark;
    private Date createTime;

}
