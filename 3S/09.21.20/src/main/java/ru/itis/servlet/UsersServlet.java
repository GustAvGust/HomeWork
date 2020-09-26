package ru.itis.servlet;

import ru.itis.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private List<User> users;

    @Override
    public void init() throws ServletException {
        this.users = new LinkedList<User>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/database_users", "postgres", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from registrated_user;");
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id"),
                                    resultSet.getString("first_name"),
                                    resultSet.getString("last_name"),
                                    resultSet.getInt("age")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usersForJsp", users);
        req.getRequestDispatcher("WEB-INF/jsp/users.jsp").forward(req, resp);
    }
}
