package com.localCompany;

/**
 * 14.09.2020
 * 02. DB
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class Car {
    private Long id;
    private String model;
    private String color;

    public Car(Long id, String model, String color, Driver driver) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.driver = driver;
    }

    // TODO: на интерес
    private Driver driver;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", driver=" + driver +
                '}';
    }
}