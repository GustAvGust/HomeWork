package ru.itis.servlet;

import ru.itis.entities.User;
import ru.itis.repositories.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/html/registration.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String age = req.getParameter("age");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");
            UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);
            usersRepositoryJdbc.save(User.builder()
                                        .firstName(firstName)
                                        .lastName(lastName)
                                        .age(Integer.parseInt(age))
                                        .login(login)
                                        .password(password)
                                        .build());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/signIn");
    }
}
