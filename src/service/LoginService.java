package service;

import domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 13:29 2017/11/24
 * @Modified By:
 */
public class LoginService {

    private static List<User> userList = new ArrayList<>();

    static{
        userList.add(new User("user_a","123"));
        userList.add(new User("user_b","123"));
        userList.add(new User("user_c","123"));
    }

    public User login(String username,String password){
        for(User user:userList){
            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User findUser(String username){
        for(User user:userList){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}