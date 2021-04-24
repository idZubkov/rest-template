package edu.zubkov.crudapp.services;

import edu.zubkov.crudapp.models.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void deleteById(long id);

    void update(User user, long id);

    User getById(long id);

    List<User> getAllUsers();

    User findByUsername(String username);
}