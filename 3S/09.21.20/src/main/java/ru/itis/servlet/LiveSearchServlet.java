package ru.itis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.entities.User;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class LiveSearchServlet extends HttpServlet {

    private List<User> users = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/html/search.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");

            UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);

            //String prefixOfFirstName = req.getParameter("search_prefix");
            String prefixOfFirstName = req.getReader().readLine();

            //!!!
            //System.out.println(prefixOfFirstName);

            if (prefixOfFirstName != null) {
                users.addAll(usersRepositoryJdbc.findUsersByPrefixOfFirstName(prefixOfFirstName));
            } else {
                users.addAll(usersRepositoryJdbc.findAll());
            }

            String usersAsJson = objectMapper.writeValueAsString(users);
            resp.setContentType("application/json");
            resp.getWriter().println(usersAsJson);

            users = new ArrayList<>();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
