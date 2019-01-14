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
import java.util.List;


@Getter
@Setter
@ToString
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private transient String password;
    private String email;
    private String mobile;
    private String realName;
    private Integer type;
    private Integer status;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;


    /*****************************以下字段 不是 数据库的持久化字段，作为展示使用的额外字段**********************************/
    private List<String> roleIdList;
    private List<String> userIds;
    private String orgId;//结构编码
    private String orgName;//机构名称
    private String roleNames;//多角色名称


//工作台对应的编码，名称，类型
//接种台	2
//儿保台	3
//预检台	4
//登记台	1


//    登记
    private String registerTowerId;   //登记台编码id
    private String registerTowerName;  //登记台名称
    private Integer registerTowerType;  //登记台类型
//    接种
    private String inoculateTowerId;   //接种台编码
    private String inoculateTowerName; //接种台名称
    private Integer inoculateTowerType;//接种台类型
//    儿保
    private String childProtectionTowerId; //儿保台编码
    private String childProtectionTowerName; //儿保台名称
    private Integer childProtectionTowerType;//儿保台类型
//    预检
    private String preCheckId;     //预检台编码
    private String preCheckName;  //预检台名称
    private Integer preCheckType;//预检台类型


}
