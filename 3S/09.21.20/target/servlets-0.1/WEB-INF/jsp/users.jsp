<%@ page import="java.util.List" %>
<%@ page import="ru.itis.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 27.09.2020
  Time: 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Age</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("usersForJsp");
            for (int i = 0; i < users.size(); i++) {
                %>
                <tr>
                    <td><%=users.get(i).getId()%></td>
                    <td><%=users.get(i).getFirstName()%></td>
                    <td><%=users.get(i).getLastName()%></td>
                    <td><%=users.get(i).getAge()%></td>
                </tr>
            <%}%>
    </table>
</body>
</html>
