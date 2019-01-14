package io.yfjz.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-12-25 8:37
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolString implements Serializable{

    /**
     * 处理可多选的下拉列表,转为List<String>
     * @用法 params.put("committee",list);Mybatis调用
     * tcio.chil_committee IN
     *  <foreach item="item" index="index" collection="committee" open="(" separator="," close=")">
     *       #{item}
     *  </foreach>
     * @param object
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/25 8:43
    */
    public final static List<String> stringToList(final Object object){
        List<String> list = new ArrayList<>();
        try {
            list = Arrays.asList((String[])object);
        } catch (Exception e) {
            list = Arrays.asList(object.toString());
        }
        return list;
    }

    /**
     * 处理可多选的下拉列表,转为指定mapKey的Map<List<String>>
     * @用法 params.put("committee",list);Mybatis调用
     * tcio.chil_committee IN
     *  <foreach item="item" index="index" collection="mapKey" open="(" separator="," close=")">
     *       #{item}
     *  </foreach>
     * @param object
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/25 8:43
     */
    public final static Map<String,Object> stringToMapList(final Object object,final String mapKey){
        final Map<String,Object> params = new HashMap<String,Object>();
        List<String> list = new ArrayList<>();
        try {
            list = Arrays.asList((String[])object);
        } catch (Exception e) {
            list = Arrays.asList(object.toString());
        }
        params.put(mapKey,list);
        return params;
    }
}