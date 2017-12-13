package servlet;

import domain.User;
import service.LoginService;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 13:45 2017/11/24
 * @Modified By:
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginService loginService = new LoginService();

        User user = loginService.login(username, password);

        if (user == null) {
            request.setAttribute("message", "用户名或密码错误");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("user", user);

        boolean remember = Boolean.parseBoolean(request.getParameter("remember"));

        //给客户机发送自动登录的cookie
        Cookie cookie = makeCookie(user);
        response.addCookie(cookie);
        response.sendRedirect("/JavaWeb_Study/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    //构造Cookie
    private Cookie makeCookie(User user) {
        long currentTime = System.currentTimeMillis();
        //构造出cookie的值
        String cookieValue = user.getUsername() + ":" + (currentTime + 5 * 60 * 1000) + ":" + md5(user.getUsername(), user.getPassword(), (currentTime + 5 * 60 * 1000));
        Cookie cookie = new Cookie("autologin", cookieValue);
        //设置cookie的保存时间为5分钟
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/JavaWeb_Study");
        return cookie;
    }

    //对CookieValue进行md5加密
    private String md5(String username, String password, long expirestime) {
        try {
            String value = password + ":" + expirestime + ":" + username;
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(value.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
