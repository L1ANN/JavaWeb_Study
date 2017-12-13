package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 13:12 2017/12/12
 * @Modified By:
 */
public class JdbcUtils {
    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;

    static{
        try{
            //读取db.properties文件中的数据库连接信息
            InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties p = new Properties();
            p.load(in);

            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");

            Class.forName(driver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    public static void release(Connection conn, Statement st, ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            rs = null;
        }

        if(st!=null){
            try{
                st.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            st=null;
        }

        if(rs!=null){
            try{
                rs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            rs =null;

        }
    }
}
