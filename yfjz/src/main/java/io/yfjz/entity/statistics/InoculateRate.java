package io.yfjz.entity.statistics;

import lombok.Getter;
import lombok.Setter;

/**
 * 接种率统计
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-23  17:20
 **/
@Getter
@Setter
public class InoculateRate extends Object{
    /**疫苗名称*/
    private String planName;  //疫苗名称
    /**疫苗类别名称*/
    private String planId ;//疫苗类别名称
    private Integer agentTimes;  //疫苗剂次
    private Integer yinCount;//应种数
    private Integer factCount;//实种数
    private Double inoculateRate;//接种率
    private Integer timelyCount;//及时数
    private Double timelyRate;//及时率
    private Integer qualifiedCount;//合格数
    private Double qualifiedRate;//合格率
    private Integer shortCount;//间短接种数（邓召仕新增）
    private Double shortRate;//间短接种率（邓召仕新增）
    private Integer outQualifiedCount;//超期数
    private Double outQualifiedRate;//超期率
    private Integer yinweiCount;//因种未种数
    private Double yinweiRate;//因种未种率

}
