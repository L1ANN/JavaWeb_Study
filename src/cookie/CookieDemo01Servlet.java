package cookie;

import javax.servlet.http.Cookie;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Author:L1ANN
 * @Description: 获取用户上一次访问的时间
 * @Date:Created in 11:22 2017/11/6
 * @Modified By:
 */
public class CookieDemo01Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //设置服务器以UTF-8编码进行输出
        response.setCharacterEncoding("UTF-8");
        //设置浏览器以UTF-8进行接收，解决中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        //获取浏览器访问服务器时传递过来的cookie数组
        Cookie[] cookies = request.getCookies();
        //如果用户是第一次访问，那得到的cookies是null
        if(cookies!=null){
            writer.print("您上一次访问的时间是:");
            for(int i=0;i<cookies.length;i++){
                Cookie cookie = cookies[i];
                if(cookie.getName().equals("lastAccessTime")){
                    Long lastAccessTime = Long.parseLong(cookie.getValue());
                    Date date = new Date(lastAccessTime);
                    writer.write(date.toString());
                }
            }
        }else{
            writer.print("这是您第一次访问本站！");
        }
        //用户访问过后重新设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
        Cookie cookie = new Cookie("lastAccessTime",String.valueOf(System.currentTimeMillis()));
        //设置Cookie的有效期为1天，1代表1s
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}
