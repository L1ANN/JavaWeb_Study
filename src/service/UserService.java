package service;

import dao.UserDao;
import dao.UserDaoImpl;
import domain.User;
import exception.UserExistException;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 14:35 2017/12/12
 * @Modified By:
 */
public class UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 提供注册服务
     */
    public void registerUser(User user) throws UserExistException {
        if (userDao.find(user.getUsername()) != null) {
            throw new UserExistException("注册的用户名已存在!");
        }
        userDao.add(user);
    }

    /**
     * 提供查找用户资料服务
     * @param username
     * @return
     */
    public User findUser(String username) {
        return userDao.find(username);
    }

    /**
     * 提供上传头像服务
     */
    public void updaePic(User user){
        userDao.addPic(user);
    }
}
