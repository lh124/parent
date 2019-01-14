//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.entity.sys;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @class_name: SysConfigEntity
 * @describe: 数据字典,带有类型
 * 说明： 在使用时,根据ttype类型,key关键字联合查询
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date:  2017/12/20 13:15
 **/

@Getter
@Setter
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = -8333236261960077825L;
    private String id;
    private String ttype;
    private String text;
    private String value;
    private String remark;
    private Integer sortOrder;
    private Integer status;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;

}
