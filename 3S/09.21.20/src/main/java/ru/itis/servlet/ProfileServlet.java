package ru.itis.servlet;

import ru.itis.entities.User;
import ru.itis.repositories.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");
        String password = (String) req.getSession().getAttribute("password");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");
            UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);

            Cookie cookie = new Cookie("reg_cookie", usersRepositoryJdbc.findByLoginPassword(login, password).getUUID());

            System.out.println(cookie.getValue());

            if (cookie.getValue() == null) {
                cookie.setValue(UUID.randomUUID().toString());
                usersRepositoryJdbc.update(User.builder()
                                                .login(login)
                                                .password(password)
                                                .UUID(cookie.getValue())
                                                .build());
                System.out.println(cookie.getValue());
            }

            resp.addCookie(cookie);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        /*Cookie cookie = new Cookie("reg_cookie", UUID.randomUUID().toString());
        resp.addCookie(cookie);*/
        req.getRequestDispatcher("WEB-INF/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String colorValue = req.getParameter("color");
        resp.addCookie(new Cookie("color", colorValue));
        /*Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("reg_cookie")) {
                System.out.println(cookie.getValue());
            }
        }*/
        resp.sendRedirect("/profile");
    }
}
