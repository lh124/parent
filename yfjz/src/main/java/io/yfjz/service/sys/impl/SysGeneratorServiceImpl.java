//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;

import io.yfjz.dao.sys.SysGeneratorDao;
import io.yfjz.service.sys.SysGeneratorService;
import io.yfjz.utils.GenUtils;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {
    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    public SysGeneratorServiceImpl() {
    }

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return this.sysGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.sysGeneratorDao.queryTotal(map);
    }

    public Map<String, String> queryTable(String tableName) {
        return this.sysGeneratorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return this.sysGeneratorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        String[] var7 = tableNames;
        int var6 = tableNames.length;
        for(int var5 = 0; var5 < var6; ++var5) {
            String tableName = var7[var5];
            //查询出表结构的基本信息
            // tablename(f_file_mgr) engine(InnoDB)	tableComment(文件档案管理)  createTime(2017-12-24 12:36:35)
            /*
                select table_name tableName, engine, table_comment tableComment,
                    create_time createTime from information_schema.tables
                where table_schema = (select database()) and table_name = 'f_file_mgr';
             */
            Map table = this.queryTable(tableName);

            //表的字段，类型，注释，主键，是否自增  额外信息
            /*
                select column_name columnName, data_type dataType, column_comment columnComment,
                column_key columnKey, extra from information_schema.columns
                where table_name = 'f_file_mgr' and table_schema = (select database())
                order by ordinal_position
             */
            List columns = this.queryColumns(tableName);
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
