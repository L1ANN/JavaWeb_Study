package session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 12:38 2017/11/6
 * @Modified By:
 */
@WebServlet(name = "SessionDemo01Servlet")
public class SessionDemo01Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setCharacterEncoding("UTF-8");
       response.setContentType("text/html;charset=UTF-8");
       //使用request对象的getSession（）获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute("data","L1ANN");
        //获取session的id
        String sessionId = session.getId();
        //判断session是不是新建的
        if(session.isNew()){
            response.getWriter().print("session创建成功，session的id是："+session);
        }else{
            response.getWriter().print("服务器已经存在该session了，session的id是："+sessionId);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
