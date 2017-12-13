package filter;

import domain.User;
import service.LoginService;
import sun.misc.BASE64Encoder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 14:21 2017/11/24
 * @Modified By:
 */
@WebFilter(filterName = "AutoLoginFilter")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //1.先检查用户是否登录，如果用户登录直接放行
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            chain.doFilter(request, response);
            return;
        }

        //2.如果用户没有登录，执行自动登录逻辑
        Cookie autoLoginCookie = null;
        Cookie[] cookies = request.getCookies();
        //小心：如果用户是第一次访问，那么得到的cookies将是null
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("autologin")) {
                autoLoginCookie = cookies[i];
            }
        }

        //如果用户没有带自动登录的cookie，就不帮其自动登录，所以要放行
        if (autoLoginCookie == null) {
            chain.doFilter(request,response);
            return;
        }

        //如果用户带了自动登录的cookie，则先检查cookie的有效期
        String[] values = autoLoginCookie.getValue().split(":");

        //①如果cookie没有分成三段，表示cookie格式不正确
        if(values.length!=3){
            chain.doFilter(request,response);
            return;
        }
        //②当前时间值大于cookie失效时间值，即用户带过来的cookie过期
        long expirestime = Long.parseLong(values[1]);
        if(System.currentTimeMillis()>expirestime){
            chain.doFilter(request,response);
            return;
        }

        //③检查cookie的有效性
        String username = values[0];
        String client_md5 = values[2];

        //检查用户名的有效性
        LoginService loginService = new LoginService();
        user = loginService.findUser(username);
        if(user==null){
            chain.doFilter(request,response);
            return;
        }

        //检查密码的有效性
        String server_md5 = md5(user.getUsername(),user.getPassword(),expirestime);
        if(!server_md5.equals(client_md5)){
            chain.doFilter(request,response);
            return;
        }

        //执行自动登录
        request.getSession().setAttribute("user",user);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private String md5(String username,String password,long expirestime){
        try{
            String value = password+":"+expirestime+":"+username;
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(value.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
