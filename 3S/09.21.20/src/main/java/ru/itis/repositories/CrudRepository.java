package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    void save(T t);
    void update(T t);
    void delete(T t);
    void deleteById(Long id);
}
