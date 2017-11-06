package cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Author:L1ANN
 * @Description: 删除cookie
 * @Date:Created in 12:14 2017/11/6
 * @Modified By:
 */
@WebServlet(name = "CookieDemo02Servlet")
public class CookieDemo02Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建一个名为lastAccessTime的cookie
        Cookie cookie = new Cookie("lastAccessTime",System.currentTimeMillis()+"");
        //设置cookie的有效期为0，命令该浏览器删除该cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
