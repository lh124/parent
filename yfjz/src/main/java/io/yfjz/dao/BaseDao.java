//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao;

import java.util.List;
import java.util.Map;


public interface BaseDao<T> {
    void save(T var1);

    void save(Map<String, Object> var1);

    void saveBatch(List<T> var1);

    int update(T var1);

    int update(Map<String, Object> var1);

    int delete(Object var1);

    int delete(Map<String, Object> var1);

    int deleteBatch(Object[] var1);

    T queryObject(Object var1);

    List<T> queryList(Map<String, Object> var1);

    List<T> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    List<Map<String,Object>> queryRealAndSetTempList(Map<String, Object> var1);

    List<Map<String,Object>> queryListMap(Map<String, Object> map);
}
