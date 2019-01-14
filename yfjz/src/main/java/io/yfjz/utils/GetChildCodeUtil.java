package io.yfjz.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author: 饶士培
 * @Date: 2018-09-28 13:35
 * @Description:
 * @tel:18798010686
 * @qq:1013147559
 */
public class GetChildCodeUtil {
    static Connection conn = null;
    public static String getChildCode(){
        String orgId = Constant.GLOBAL_ORG_ID;
        String years = "9018";
        String orgYear = orgId.concat(years);
        String chilCode = "";
        String sql = "select max(chil_code) as chil_code from t_child_info WHERE chil_code LIKE '%"+orgYear+"%'";
         conn = getConnection();
        try{
            PreparedStatement preparedStatement =conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               String maxChildCode = resultSet.getString("chil_code");
               if(maxChildCode==null){
                   chilCode = orgYear.concat("0001");
               }else {
                   Long newChildCode = Long.parseLong(maxChildCode) + 1;
                   chilCode = newChildCode.toString();
               }
            }
            //关闭资源
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }catch (Exception e){

        }

        return chilCode;
    }

    public static Connection getConnection() {
        String url = PropertiesUtils.getProperty("db.properties", "jdbc.url");
        String username = PropertiesUtils.getProperty("db.properties", "jdbc.username");
        String password = PropertiesUtils.getProperty("db.properties", "jdbc.password");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {

        }
        return conn;
    }

    public static void main(String[] args) {
       /* String orgId = "5224240101";
        String years = "9018";
        String orgYear = orgId.concat(years);
        String sql = "select max(chil_code) as chil_code from t_child_info WHERE chil_code LIKE '%"+orgYear+"%'";
        JdbcUtil jdbc = new JdbcUtil();
        Connection conn = jdbc.getConnection();
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        try{
            PreparedStatement preparedStatement =conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

             //4.处理数据库的返回结果(使用ResultSet类)
             while(resultSet.next()){
                     System.out.println(resultSet.getString("chil_code"));

                 Long newChildCode = Long.parseLong(resultSet.getString("chil_code"))+1;
                 System.out.println(newChildCode);
                 }
                 //关闭资源
                resultSet.close();
                preparedStatement.close();
                 conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}