<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 07.10.2020
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1 style="color: ${cookie.get("color").value}">Profile</h1>
    <form action="/profile" method="post">
        <select name="color">
            <option value="red">RED</option>
            <option value="green">GREEN</option>
        </select>
        <input type="submit" value="OK">
    </form>
</body>
</html>
