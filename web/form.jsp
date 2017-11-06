<%--
  Created by IntelliJ IDEA.
  User: L1ANN
  Date: 2017/11/6
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form表单</title>
    <script type="text/javascript">
        //表单是否已经提交标识，默认为false
        var isCommitted = false;
        function dosubmit(){
            //获取表单提交按钮
            var btnSubmit = document.getElementById("submit");
            //将表单提交按钮设置为不可用，这样就可以避免用户再次点击提交按钮
            btnSubmit.disabled="disabled";
            return true;
        }


    </script>
</head>
<body>
<form action="DoForm.do" onsubmit="return dosubmit()" method="post">
    <input type="hidden" name="token" value="${token}"/>
    用户名：<input type="text" name="username">
    <input type="submit" value="提交" id="submit">
</form>
</body>
</html>
