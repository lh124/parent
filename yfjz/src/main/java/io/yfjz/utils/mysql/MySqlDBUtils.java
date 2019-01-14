package io.yfjz.utils.mysql;

import io.yfjz.utils.PropertyUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 刘琪
 * @describe: MySql数据库连接工具类
 * @class_name: MySqlDBUtils
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-12-14  14:03
 **/
public class MySqlDBUtils {

    /**
     * @method_name: getConnection
     * @describe: 获取mysql数据库连接
     * @param []
     * @return java.sql.Connection
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-14  14:12
     **/
    public static Connection getConnection() throws Exception {
        String jdbcDriver= PropertyUtils.getValue("jdbc.driverName");
        Class.forName(jdbcDriver);
       return DriverManager.getConnection(PropertyUtils.getValue("jdbc.url"), PropertyUtils.getValue("jdbc.username"),PropertyUtils.getValue("jdbc.password"));
    }

    /**
     * @method_name: insert
     * @describe: 大数据量插入
     *              说明：此方法作为百万级别的数据插入数据库，性能最好，耗时最少
     *              此方法只作为一个demo,不做实际开发
     * @param []
     * @return void
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-14  11:10
     **/
    public static void insert() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            String prefix = "INSERT INTO test_insert (count, create_time, random) VALUES ";
            StringBuffer suffix = new StringBuffer();
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement("");
            for (int i = 1; i <= 1000; i++) {
                for (int j = 1; j <= 100; j++) {
                    suffix.append("(" + j * i + ", SYSDATE(), " + i * j * Math.random() + "),");
                }
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                pst.addBatch(sql);
                pst.executeBatch();
                conn.commit();
                suffix = new StringBuffer();
            }
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}