package servlet;

import domain.User;
import exception.UserExistException;
import formbean.RegisterFormBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import service.UserService;
import util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 17:23 2017/12/12
 * @Modified By:
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将客户端提交的表单数据封装到RegisterFormBean对象中
        RegisterFormBean formbean = WebUtils.request2Bean(request, RegisterFormBean.class);
        //校验用户注册填写的表单数据
        if(formbean.validate() == false){
            //将封装了用户填写的表单数据的formbean对象发送回register.jsp页面的form表单进行显示
            request.setAttribute("formbean",formbean);
            //校验失败就说明是用户填写的表单数据有问题，那么就跳转回register.jsp
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request,response);
            return;
        }
        User user = new User();
        try{
            //注册字符串到日期的转换器
            ConvertUtils.register(new DateLocaleConverter(),Date.class);
            //将表单中的数据填充到javabean中
            BeanUtils.copyProperties(user,formbean);
            //设置用户的Id属性
            user.setId(WebUtils.makeId());
            //调用service层提供的注册用户服务实现用户注册
            UserService userService = new UserService();
            userService.registerUser(user);
            String message = String.format("注册成功！3s后为您自动跳转到登录页面！！<meta http-equiv='refresh' content='3;url=%s'/>",request.getContextPath()+"/servlet/LoginUIServlet");
            request.setAttribute("message",message);
            request.getRequestDispatcher("/message.jsp").forward(request,response);
        }catch(UserExistException e){
            formbean.getErrors().put("username","注册用户已存在！");
            request.setAttribute("formbean",formbean);
            request.getRequestDispatcher("/message.jsp").forward(request,response);
        }catch(Exception e){
            e.printStackTrace();//在后台记录异常
            request.setAttribute("message","对不起，注册失败！");
            request.getRequestDispatcher("/message.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
