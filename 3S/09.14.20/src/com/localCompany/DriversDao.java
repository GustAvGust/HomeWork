package com.localCompany;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 14.09.2020
 * 02. DB
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
// DATA ACCESS OBJECT
public class DriversDao {

    private Connection connection;

    public DriversDao(Connection connection) {
        this.connection = connection;
    }

    // TODO: реализовать
    public Optional<Driver> findById(Long id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM driver");

        while(resultSet.next()) {
            if(resultSet.getLong("id") == id) {
                /*List<Car> cars = findCarsByDriversId(id);*/
                Driver dr = new Driver(resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"), new LinkedList<Car>());
                List<Car> cars = findCarsByDriver(dr);
                dr.setCars(cars);
                return Optional.of(dr);
            }
        }
        return null;
    }

    private List<Car> findCarsByDriver(Driver dr) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM car");
        List<Car> cars = new LinkedList<>();

        while(resultSet.next()) {
            if(resultSet.getLong("driver_id") == dr.getId()) {
                cars.add(new Car(resultSet.getLong("id"),
                                    resultSet.getString("model"),
                                    resultSet.getString("color"),
                                    dr));
            }
        }
        return cars;
    }
}
