package ru.itis.repositories;

import ru.itis.entities.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;


public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_FIND_ALL_USERS = "select * from registrated_user";
    //language=SQL
    private static final String SQL_FIND_ALL_USERS_BY_AGE = "select * from registrated_user where age = ?";

    private Connection connection;
    private RowMapper<User> usersRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .UUID(row.getString("uuid"))
            .login(row.getString("login"))
            .build();
    private RowMapper<Boolean> booleanRowMapper = row -> row.getBoolean("exists");

    private SimpleJdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        this.jdbcTemplate = new SimpleJdbcTemplate(connection);
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        return jdbcTemplate.queryForList(SQL_FIND_ALL_USERS_BY_AGE, usersRowMapper, age);
    }

    @Override
    public boolean isExistsWithLoginPassword(String login, String password) {
        //language=SQL
        final String SQL_IS_EXISTS_WITH_LOGIN_PASSWORD = "select exists(select 1 from registrated_user where login = ? and password = ?);";
        /*//language=SQL
        final String SQL_IS_EXISTS_WITH_LOGIN_PASSWORD = "select exists(select 1 from registrated_user where login = '" + login + "' and password = '" + password + "');";*/
        return jdbcTemplate.queryForList(SQL_IS_EXISTS_WITH_LOGIN_PASSWORD, booleanRowMapper, login, password).get(0);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.queryForList(SQL_FIND_ALL_USERS, usersRowMapper);
    }

    // add new user in DB
    @Override
    public void save(User user) {
        //language=SQL
        final String SQL_ADD_NEW_USER = "insert into registrated_user(first_name, password, last_name, age, login) " +
                "values('" + user.getFirstName() + "', '" + user.getPassword() + "', '" + user.getLastName() + "', " + user.getAge() + ", '" + user.getLogin() + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_ADD_NEW_USER);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //just uuid update!!!
    @Override
    public void update(User user) {

        //language=SQL
        /*final String SQL_UPDATE_USER = "update registrated_user set " +
                "first_name = " + user.getFirstName() + ", " +
//                "password = " + user.getPassword() + ", " +
                "last_name = " + user.getLastName() + ", " +
                "age = " + user.getAge() + ", " +
                "uuid = " + user.getUUID() + ", " +
//                "login = " + user.getLogin() + " " +
                "where id = ?;";*/

        final String SQL_UUID_UPDATE = "update registrated_user set uuid = '" + user.getUUID() + "' " +
                "where login = '" + user.getLogin() + "' and password = '" + user.getPassword() + "';";

        try {
            Statement statement = connection.createStatement();

            /*if (this.isExistsWithLoginPassword(user.getLogin(), user.getPassword())) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
                preparedStatement.setLong(1, this.findByLoginPassword(user.getLogin(), user.getPassword()).getId());
                preparedStatement.executeUpdate();}*/
            if (this.isExistsWithLoginPassword(user.getLogin(), user.getPassword())) {
                statement.executeUpdate(SQL_UUID_UPDATE);
            } else {
                throw new SQLException("User doesn't exist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(Long id) {

    }

    public User findByLoginPassword(String login, String password) {
        //language=SQL
        final String SQL_FIND_BY_LOGIN_PASSWORD = "select * from registrated_user where login = ? and password = ?";
        return jdbcTemplate.queryForList(SQL_FIND_BY_LOGIN_PASSWORD, usersRowMapper, login, password).get(0);
    }
}
