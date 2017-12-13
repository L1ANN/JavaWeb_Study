<%--
  Created by IntelliJ IDEA.
  User: L1ANN
  Date: 2017/11/24
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/Login.do" method="post">
    用户名：<input type="text" name="username" placeholder="用户名">
    密码：<input type="password" name="password" placeholder="密码">
    记住密码：<input type="radio" name="remember" value="true">
    <input type="submit" value="提交">

</form>
</body>
</html>
