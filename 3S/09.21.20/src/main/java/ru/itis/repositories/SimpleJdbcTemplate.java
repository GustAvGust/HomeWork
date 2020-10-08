package ru.itis.repositories;

import ru.itis.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleJdbcTemplate {

    private Connection connection;

    public SimpleJdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object ... args) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int position = 1;
            for (Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> res = new ArrayList<>();

            if (resultSet == null) {
                throw new SQLException("Empty result");
            }

            while (resultSet.next()) {
                res.add(rowMapper.mapRow(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
