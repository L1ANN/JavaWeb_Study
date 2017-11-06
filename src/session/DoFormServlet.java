package session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:L1ANN
 * @Description: 处理表单，模拟网络延迟情况
 * @Date:Created in 14:00 2017/11/6
 * @Modified By:
 */
@WebServlet(name = "DoFormServlet")
public class DoFormServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean b = isRepeatSubmit(request);
        if(b){
            System.out.println("请不要重复提交");
            return;
        }

        request.getSession().removeAttribute("token");
        String token = request.getParameter("token");
        //客户端是以UTF-8编码传输数据到服务器的，所以需要设置服务器端以UTF-8的编码进行接收，否则对于中文数据就会产生乱码
        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("username");
        System.out.println("向数据库中插入数据："+userName+" "+token);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private boolean isRepeatSubmit(HttpServletRequest request){
        String client_token = request.getParameter("token");
        //1.如果用户提交的表单数据中没有token，则用户是重复提交了表单
        if(client_token == null){
            return true;
        }
        //取出存储在Session中的token
        String server_token =(String)request.getSession().getAttribute("token");
        //2.如果当前用户的Session中不存在Token（令牌），则用户是重复提交了表单
        if(server_token == null){
            return true;
        }
        //3.存储在Session中的Token（令牌）与表单提交的Token（令牌）不同，则用户是重复提交了表单
        if(!client_token.equals(server_token)){
            return true;
        }
        return false;
    }
}
