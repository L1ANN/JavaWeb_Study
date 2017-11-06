package session;

import util.TokenProccessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 17:40 2017/11/6
 * @Modified By:
 */
@WebServlet(name = "FormServlet")
public class FormServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建令牌
        String token = TokenProccessor.getInstance().makeToken();
        System.out.println("在FormServlet中生成的token"+token);
        //使用session保存token
        request.getSession().setAttribute("token",token);
        //跳转到form.jsp
        request.getRequestDispatcher("/form.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
