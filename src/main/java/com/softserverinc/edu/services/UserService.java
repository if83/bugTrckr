package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.User;

import java.util.List;

public interface UserService {

    User getOne(Long id);
    User findOne(Long id);
    User save(User user);
    void delete(Long id);
    User update(User user);
    List<User> getAll();

    void saveOrUpdate(User user);

}
