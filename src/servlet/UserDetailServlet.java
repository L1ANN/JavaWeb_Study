package servlet;

import domain.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 13:37 2017/12/13
 * @Modified By:
 */
@WebServlet(name = "UserDetailServlet", urlPatterns = "/UserDetail.do")
public class UserDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = "admin";
        UserService service = new UserService();
        User user = service.findUser(username);

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/detailuser.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
