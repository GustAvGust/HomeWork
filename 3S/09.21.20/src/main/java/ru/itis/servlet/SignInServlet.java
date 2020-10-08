package ru.itis.servlet;

import ru.itis.entities.User;
import ru.itis.repositories.UsersRepository;
import ru.itis.repositories.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/html/signIn.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");
            UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);

            if (usersRepositoryJdbc.isExistsWithLoginPassword(login, password)) {
                req.getSession().setAttribute("login", login);
                req.getSession().setAttribute("password", password);
                resp.sendRedirect("/profile");
            } else {
                resp.getWriter().println("User doesn't exist, you must be registrated");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
