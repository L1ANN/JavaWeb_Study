package dao;

import domain.User;
import util.JdbcUtils;

import java.sql.*;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:47 2017/12/12
 * @Modified By:
 */
public class UserDaoImpl implements UserDao {

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public void add(User user){
        try {
            conn = JdbcUtils.getConnection();
            String sql = "insert into user(id,username,password,email,birthday) values(?,?,?,?,?)";
            st =conn.prepareStatement(sql);
            st.setString(1,user.getId());
            st.setString(2,user.getUsername());
            st.setString(3,user.getPassword());
            st.setString(4,user.getEmail());
            st.setDate(5,user.getBirthday());
            st.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn,st,rs);
        }
    }

    @Override
    public User find(String username){
        try{
            conn=JdbcUtils.getConnection();
            String sql = "select * from user where username=?";
            st = conn.prepareStatement(sql);
            st.setString(1,username);
            rs = st.executeQuery();
            User user = new User();
            while(rs.next()){

                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setBirthday(rs.getDate(5));
            }
            return user;
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            JdbcUtils.release(conn,st,rs);
        }
    }
}
