package com.xkpt;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); // 加载Oracle驱动程序
        } catch(InstantiationException e1) {
            System.out.println("实例异常");
        } catch(IllegalAccessException e2) {
            System.out.println("访问异常");
        } catch(ClassNotFoundException e3) {
            System.out.println("MySQL驱动类找不到");
        }
    }
    public static Connection getConnection() {      // 返回一个数据库连接
        Connection connection = null;// 创建一个数据库连接
        try {
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:xkpt";//Oracle的默认数据库名
            String user = "xkpt";// 系统默认的用户名
            String password = "123456";// 安装时设置的密码
            connection = DriverManager.getConnection(url, user, password);// 获取连接
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
