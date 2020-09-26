package ru.itis.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/registration")
public class SimpHtmlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/html/registration.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String age = req.getParameter("age");
        String password = req.getParameter("pass");

//        PrintWriter writer = resp.getWriter();
//        writer.println(name + ": " + password);
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into registrated_user(first_name, last_name, age, password) " +
                    "values('" + firstName + "', " + " '" + lastName + "'," + Integer.parseInt(age) + ", " + password +");");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
}
