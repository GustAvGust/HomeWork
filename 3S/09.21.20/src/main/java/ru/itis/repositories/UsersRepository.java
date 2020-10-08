package ru.itis.repositories;

import ru.itis.entities.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age);
    boolean isExistsWithLoginPassword(String login, String password);
}
