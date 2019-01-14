//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roleId;
    private String roleName;
    private String remark;
    private List<String> menuIdList;
    private Integer status;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;

}
