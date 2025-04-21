package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
     List<User> getAllUsers();
     void addUser(User user);
     User getUserById(Long id);
     void deleteUser(Long id);
     void save(User user);
     User findByEmail(String email);

    void updateUser(User user);
}
