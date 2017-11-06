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
 * @Description: 使用cookie存取中文
 * @Date:Created in 12:24 2017/11/6
 * @Modified By:
 */
@WebServlet(name = "CookieDemo03Servlet")
public class CookieDemo03Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("userName", URLEncoder.encode("连鹏飞", "UTF-8"));
        response.addCookie(cookie);

        String userName = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cook:cookies) {
            if(cook.getName().equals("username")) {
                userName = URLDecoder.decode(cook.getValue(), "UTF-8");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
