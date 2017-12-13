<%--
  Created by IntelliJ IDEA.
  User: L1ANN
  Date: 2017/12/13
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户详情</title>
</head>
<body>
<table>
    <tr>
        <td>用户名</td>
        <td>${user.username}</td>
    </tr>
    <tr>
        <td>头像</td>
        <c:if test="${user.pic!=null}">
            <td><img src="/pic/${user.pic}" width="100" height="100"></td>
        </c:if>
        <td>
            <form action="${pageContext.request.contextPath}/Upload.do" enctype="multipart/form-data" method="post">
                <input type="file" name="file">
                <input type="submit" value="上传">
            </form>
        </td>
    </tr>
</table>
</body>
</html>
