//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import java.util.List;
import java.util.Map;

public interface SysGeneratorService {
    List<Map<String, Object>> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    Map<String, String> queryTable(String var1);

    List<Map<String, String>> queryColumns(String var1);

    byte[] generatorCode(String[] var1);
}
