package io.yfjz.service.child;

import java.util.List;
import java.util.Map;

/**
 * 儿童接种信息
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-12-22 17:51
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public interface ChildInoculateService{

    List<Map<String,Object>> viewInocChildInfoData(final Map<String,Object> map);

    Integer viewInocChildInfoTotal(final Map<String,Object> map);

    List<Map<String,Object>> listChildInoculateData(final Map<String,Object> map);
}