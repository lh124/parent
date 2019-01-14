package io.yfjz.service.webService.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.dao.webService.DataSynchronizationDao;
import io.yfjz.entity.sys.SysDepartEntity;
import io.yfjz.service.webService.DataSynchronizationService;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.ZipUtils;
import io.yfjz.utils.encrypt.DESUtils;
import io.yfjz.webservice.SingleTableWebService;
import io.yfjz.websocket.TowerSessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 14:07 2018/09/12
 */
@Service("dataSynchronizationService")
public class DataSynchronizationServiceImpl implements DataSynchronizationService {
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DataSynchronizationServiceImpl.class);
    @Resource
    private SingleTableWebService singleTableWebService;

    @Resource
    private DataSynchronizationDao dataSynchronizationDao;

    @Override
    public SysDepartEntity queryDepartList(String orgId) {
        return dataSynchronizationDao.queryDepartList(orgId);
    }

    @Override
    public Map<String, Object> updateTableData(String orgId, String tableName, Map<String, Object> where) {
        SysDepartEntity depart = dataSynchronizationDao.queryDepartList(orgId);
        Map<String, Object> map = new HashMap<>();

        if (tableName == null || "".equals(tableName.trim())) {
            map.put("code", 10000);
            map.put("msg", "增量更新的表名不能为空！");
            return map;
        }
        if (where.containsKey("tableName")) {
            where.remove("tableName");
        }
        try {
            //判断传的条件是否为空，如果为空new一个对象
            if (where == null || where.size() == 0) {
                where = new HashMap<String, Object>();
            }
            //调用方法获取表的增量数据，返回内容加密的json字符串
            String result = singleTableWebService.findObjectsByParams(orgId, depart.getPid(), tableName, JSON.toJSONString(where));
            log.debug("返回数据为：" + result);
            JSONObject json = JSONObject.parseObject(result, JSONObject.class);
            //判断平台是否成功的返回了数据
            if (!json.getString("code").equals("10000")) {
                map.put("code", json.getString("code"));
                map.put("msg", json.getString("msg"));
                return map;
            }

            String md5 = DigestUtils.md5DigestAsHex(json.getString("content").getBytes("UTF-8"));
            //检验返回的数据是否是平台返回的
            if (json.getString("md5").equals(md5)) {
                log.debug("检验成功，开始更新数据");
            } else {
                map.put("code", 10008);
                map.put("msg", "检验错误，请联系管理员！");
                return map;
            }

            byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("WEBSERVICE_THE_KEY"));
            byte[] data = new BASE64Decoder().decodeBuffer(json.getString("content"));
            //解密
            byte[] des3DecodeECB = DESUtils.des3DecodeECB(key, data);
            byte[] des3Decode = ZipUtils.unZip(des3DecodeECB);//解压
            String content = new String(des3Decode, "UTF-8");

            JSONObject json1 = JSONObject.parseObject(content);

            List<String> updateTable = addOrupdateTableData(json1);
            log.debug("增量更新的表" + updateTable.toString());

            String[] str = updateTable.toArray(new String[updateTable.size()]);
            String acknowledge = singleTableWebService.acknowledge(orgId, str, depart.getPid());
            JSONObject json2 = JSONObject.parseObject(acknowledge, JSONObject.class);
            log.debug(tableName + "表同步增量数据成功回调信息" + json2.getString("msg"));

            map.put("code", 200);
            map.put("msg", "同步" + tableName + "表增量数据成功！");
            map.put("table", updateTable);
        } catch (Exception e) {
            log.error(this, e);
            map.put("code", 500);
            map.put("msg", "同步数据错误，请重新同步数据！");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateFullData(String orgId, String pId) {
        if (pId == null || "".equals(pId.trim())) {
            SysDepartEntity depart = dataSynchronizationDao.queryDepartList(orgId);
            pId = depart.getPid();
        }
        Map<String, Object> map = new HashMap<>();
        try {
            //String findAllNeedUpdate = singleTableWebService.findAllNeedUpdate(orgId, depart.getPid());
            String findAllNeedUpdate = singleTableWebService.findAllObjectsMultiUpdate(orgId, pId);
            log.debug("返回数据为：" + findAllNeedUpdate);
            JSONObject json = JSONObject.parseObject(findAllNeedUpdate, JSONObject.class);
            //判断平台是否成功的返回了数据
            if (!json.getString("code").equals("10000")) {
                map.put("code", json.getString("code"));
                map.put("msg", json.getString("msg"));
                return map;
            }

            String md5 = DigestUtils.md5DigestAsHex(json.getString("content").getBytes("UTF-8"));
            //检验返回的数据是否是平台返回的
            if (json.getString("md5").equals(md5)) {
                log.debug("检验成功，开始更新数据");
            } else {
                map.put("code", 10008);
                map.put("msg", "检验错误，请联系管理员！");
                return map;
            }

            byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("WEBSERVICE_THE_KEY"));
            byte[] data = new BASE64Decoder().decodeBuffer(json.getString("content"));
            //解密
            byte[] des3DecodeECB = DESUtils.des3DecodeECB(key, data);
            byte[] des3Decode = ZipUtils.unZip(des3DecodeECB);//解压
            String content = new String(des3Decode, "UTF-8");
            JSONObject json1 = JSONObject.parseObject(content);

            //遍历返回来的数据，获取到表名，在根据表名更新数据或添加数据
            List<String> updateTable = addOrupdateTableData(json1);

            log.debug("增量更新的表" + updateTable.toString());

            //执行成功后回调信息
            String acknowledge = singleTableWebService.acknowledgeByPlatform(orgId, pId);
            JSONObject json2 = JSONObject.parseObject(acknowledge, JSONObject.class);
            log.debug("全部表同步增量数据成功回调信息" + json2.getString("msg"));

            map.put("code", 200);
            map.put("msg", "同步增量数据成功！");
            map.put("table", updateTable);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(this, e);
            map.put("code", 500);
            map.put("msg", "同步数据错误，请重新同步数据！");
        }
        return map;
    }

    /**
     * 增量更新或添加数据
     *
     * @param json
     * @return
     */
    @Transactional
    public List<String> addOrupdateTableData(JSONObject json) {
        List<String> addOrupdateTable = new ArrayList();
        //如果要清除整张表数据，value值为空就可以了
        Map<String, String> relevancyTable = new HashMap<String, String>() {{
            put("sys_role_menu", "role_id");
            put("sys_user_role", "user_id");
            put("sys_depart_user", "user_id");
            put("t_base_process", null);
        }};

        //遍历返回来的数据，获取到表名，在根据表名更新数据或添加数据
        for (String key : json.keySet()) {

            //更新表名取表的数据
            JSONArray jsonArray = new JSONArray();
            Object ss = json.getObject(key, Object.class);
            //判断返回的是JSONArra还是JSONObject
            if (ss instanceof JSONObject) {
                JSONObject ject = new JSONObject();
                ject.put(key, json.getJSONObject(key));
                addOrupdateTable.add(ject.toJSONString());
                continue;
            } else if (ss instanceof JSONArray) {
                jsonArray = json.getJSONArray(key);
                addOrupdateTable.add(key);
            } else {
                addOrupdateTable.add(key);
            }

            //判断是否有指定表
            if (relevancyTable.containsKey(key)) {
                Map<String, Object> map1 = new HashMap<>();

                if (relevancyTable.get(key) == null || "".equals(relevancyTable.get(key))) {
                    dataSynchronizationDao.deleteTableData(key, map1);
                } else {
                    //遍历当前表根据条件删除数据
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject tableDate = jsonArray.getJSONObject(i);
                        //把查询条件放在Map里
                        map1.put(relevancyTable.get(key), tableDate.getString(relevancyTable.get(key)));
                        dataSynchronizationDao.deleteTableData(key, map1);
                    }
                }

                //遍历添加数据
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject tableDate = jsonArray.getJSONObject(i);
                    if (tableDate.containsKey("update_time")) {//去掉更新时间，本地不用更新时间
                        tableDate.remove("update_time");
                    }
                    //把需要添加的数据转为Map
                    Map<String, Object> jsonMap = JSONObject.toJavaObject(tableDate, Map.class);
                    dataSynchronizationDao.addTableData(key, jsonMap);
                }
            } else {
                List<String> primaryKey = new ArrayList<>();
                //遍历当前表从平台下载的数据
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject tableDate = jsonArray.getJSONObject(i);
                    if (tableDate.containsKey("update_time")) {//去掉更新时间，本地不用更新时间
                        tableDate.remove("update_time");
                    }
                    log.debug(key + "表数据：" + tableDate.toString());

                    //判断是否需要根据表名查询表主键
                    if (!tableDate.containsKey("id") && primaryKey.size() == 0) {
                        String database = PropertiesUtils.getMapValue("database");
                        primaryKey = dataSynchronizationDao.queryTableKey(key, database);
                    } else if (primaryKey.size() == 0) {
                        primaryKey.add("id");
                    }

                    //把查询条件放在Map里
                    Map<String, Object> map1 = new HashMap<>();

                    //一张表可能存在多个主键的情况，遍历查询出来的主键，存放在Map里做为条件
                    for (String tableKey : primaryKey) {
                        map1.put(tableKey, tableDate.getString(tableKey));
                    }
                    Integer count = dataSynchronizationDao.queryTableIdCount(key, map1);
                    //根据表名和表主键查询是否当前更新的数据，如果有就更新，没有就添加到表中
                    if (count > 0) {//更新数据
                        //把需要更新的数据转为Map
                        Map<String, Object> jsonMap = JSONObject.toJavaObject(tableDate, Map.class);
                        dataSynchronizationDao.updateTableData(key, jsonMap, map1);
                    } else {//添加数据
                        //把需要添加的数据转为Map
                        Map<String, Object> jsonMap = JSONObject.toJavaObject(tableDate, Map.class);
                        dataSynchronizationDao.addTableData(key, jsonMap);
                        if (key.equals("t_base_tower")) {
                            TowerSessionUtils.add(jsonMap.get("id").toString());
                        }
                    }
                }
            }
        }

        Integer count = dataSynchronizationDao.updateTableData("sys_user",
                new HashMap<String, Object>() {{
                    put("delete_status", "1");
                    put("status", "0");
                }},
                new HashMap<String, Object>() {{
                    put("username", "admin");
                }});

        return addOrupdateTable;
    }

    @Override
    public Map<String, Object> updateFullData(String orgId, String pId, String userId) {
        if (pId == null || "".equals(pId.trim())) {
            SysDepartEntity depart = dataSynchronizationDao.queryDepartList(orgId);
            pId = depart.getPid();
        }
        Map<String, Object> map = new HashMap<>();
        try {
            //String findAllNeedUpdate = singleTableWebService.findAllNeedUpdate(orgId, depart.getPid());
            String findAllNeedUpdate = singleTableWebService.findAllUpdate(orgId, pId);
            log.debug("返回数据为：" + findAllNeedUpdate);
            JSONObject json = JSONObject.parseObject(findAllNeedUpdate, JSONObject.class);
            //判断平台是否成功的返回了数据
            if (!json.getString("code").equals("10000")) {
                map.put("code", json.getString("code"));
                map.put("msg", json.getString("msg"));
                return map;
            }

            String md5 = DigestUtils.md5DigestAsHex(json.getString("content").getBytes("UTF-8"));
            //检验返回的数据是否是平台返回的
            if (json.getString("md5").equals(md5)) {
                log.debug("检验成功，开始更新数据");
            } else {
                map.put("code", 10008);
                map.put("msg", "检验错误，请联系管理员！");
                return map;
            }

            byte[] key = new BASE64Decoder().decodeBuffer(PropertiesUtils.getMapValue("WEBSERVICE_THE_KEY"));
            byte[] data = new BASE64Decoder().decodeBuffer(json.getString("content"));
            //解密
            byte[] des3DecodeECB = DESUtils.des3DecodeECB(key, data);
            byte[] des3Decode = ZipUtils.unZip(des3DecodeECB);//解压
            String content = new String(des3Decode, "UTF-8");
            JSONObject json1 = JSONObject.parseObject(content);

            //遍历返回来的数据，获取到表名，在根据表名更新数据或添加数据
            List<String> updateTable = addOrupdateTableAllData(json1);

            log.debug("增量更新的表" + updateTable.toString());

            //执行成功后回调信息
            String acknowledge = singleTableWebService.acknowledgeByPlatform(orgId, pId);
            JSONObject json2 = JSONObject.parseObject(acknowledge, JSONObject.class);
            log.debug("全部表同步增量数据成功回调信息" + json2.getString("msg"));

            map.put("code", 200);
            map.put("msg", "同步增量数据成功！");
            map.put("table", updateTable);
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(this, e);
            map.put("code", 500);
            map.put("msg", "同步数据错误，请重新同步数据！");
        }
        return map;
    }


    @Transactional
    public List<String> addOrupdateTableAllData(JSONObject json) {
        List<String> addOrupdateTable = new ArrayList();

        //遍历返回来的数据，获取到表名，在根据表名更新数据或添加数据
        for (String key : json.keySet()) {
            //更新表名取表的数据
            JSONArray jsonArray = new JSONArray();
            Object ss = json.getObject(key, Object.class);

            //判断返回的是JSONArra还是JSONObject
            if (ss instanceof JSONObject) {

                JSONObject ject = new JSONObject();
                ject.put(key, json.getJSONObject(key));
                addOrupdateTable.add(ject.toJSONString());
                continue;
            } else if (ss instanceof JSONArray) {

                jsonArray = json.getJSONArray(key);
                addOrupdateTable.add(key);
            } else {
                addOrupdateTable.add(key);
            }

            //判断当前这张表是否有更新数据
            if(jsonArray.size()>0){
                //清空当前表数据
                dataSynchronizationDao.deleteTableData(key, new HashMap<>());
            }
            List<String> primaryKey = new ArrayList<>();
            //遍历当前表从平台下载的数据
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tableDate = jsonArray.getJSONObject(i);
                if (tableDate.containsKey("update_time")) {//去掉更新时间，本地不用更新时间
                    tableDate.remove("update_time");
                }
                log.debug(key + "表数据：" + tableDate.toString());
                //把需要添加的数据转为Map
                Map<String, Object> jsonMap = JSONObject.toJavaObject(tableDate, Map.class);
                dataSynchronizationDao.addTableData(key, jsonMap);

                log.info("判断表是不是工作台表(t_base_tower)，如果是就把当前工作台添加到缓存中 key:"+key);
                if (key.equals("t_base_tower")) {
                    //判断当前工作台的数据是否已经被删除了
                    if("0".equals(jsonMap.get("delete_status").toString())){
                        TowerSessionUtils.add(jsonMap.get("id").toString());
                    }else if ("1".equals(jsonMap.get("delete_status").toString())){
                        String id = jsonMap.get("id").toString();
                        Iterator<String> it = TowerSessionUtils.towers.iterator();
                        while (it.hasNext()){
                            String next = it.next();
                            if (next.equals(id)){
                                it.remove();
                            }
                        }
                    }

                }
            }
        }

        Integer count = dataSynchronizationDao.updateTableData("sys_user",
                new HashMap<String, Object>() {{
                    put("delete_status", "1");
                    put("status", "0");
                }},
                new HashMap<String, Object>() {{
                    put("username", "admin");
                }});

        return addOrupdateTable;
    }

}
