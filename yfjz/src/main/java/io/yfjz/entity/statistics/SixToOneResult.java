package io.yfjz.entity.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * create by tianjinhai on 2018/9/29 11:01
 */
@Setter
@Getter
@ToString
public class SixToOneResult {
    //行政村
    private String committee;
    //疫苗英文名称
    private String vaccine;
    //疫苗编码用于上传报表
    private String bioCode;
    //剂次
    private Integer times;
    //本地应种数
    private Integer localShould;
    //流动应种数
    private Integer moveShould;
    //本地实种数
    private Integer localReal;
    //流动实种数
    private Integer moveReal;
}
