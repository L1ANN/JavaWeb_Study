package session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 10:54 2017/11/10
 * @Modified By:
 */
@WebServlet(name = "BuyServlet")
public class BuyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Book book = DB.getAll().get(id); //得到用户要买的书
        HttpSession session = request.getSession();
        List<Book> list = (List)session.getAttribute("list");
        if(list == null){
            list = new ArrayList<>();
            list.add(book);
            session.setAttribute("list",list);
        }
        list.add(book);
        String url = response.encodeRedirectURL("ListCart.do");
        System.out.println(url);
        response.sendRedirect(url);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
