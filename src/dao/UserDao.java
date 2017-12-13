package dao;

import domain.User;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:46 2017/12/12
 * @Modified By:
 */
public interface UserDao {
    /**
     * 添加用户
     */
    void add(User user);

    /**
     * 查找用户
     */
    User find(String username);
}
