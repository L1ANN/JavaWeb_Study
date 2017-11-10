package session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 11:08 2017/11/10
 * @Modified By:
 */
@WebServlet(name = "ListCartServlet")
public class ListCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<Book> list = (List)session.getAttribute("list");
        if(list==null||list.size()==0){
            out.write("no book list");
            return;
        }
        //显示用户购买的商品
        out.write("The book you buy:<br>");
        for(Book book:list){
            out.write(book.getName()+"<br/>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
