package io.yfjz.entity.basesetting;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 疫苗分类实体类
 * @Date 16:12 2018/07/31
 */
@Getter
@Setter
@ToString
public class TVacClassEntity implements Serializable{
    private static final long serialVersionUID = 2555828898535617382L;

    private String classCode;//种类编号
    private String className;//种类名称
    private String classCnName;//种类中文简称
    private String classEnName;//种类英文名称
    private String fkCreateUserId;//创建人
    private Integer sortOrder;//排序字段  默认排序的参数，从0开始
    private String attention;//疫苗说明
    private Integer status;//状态
    private Integer deleteStatus;//删除状态：0：正常；1：删除
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    //拼音首字母
    private String pinyinInitials;
}
