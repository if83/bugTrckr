package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User saveUser(User user);

    void deleteById(Long id);

    User updateUser(User user);

    List<User> getListOfAllUsers();

    User getUserByEmail(String email);

    List<User> getListOfUsersByRole(UserRole role);

    List<User> getListOfUsersByFirstNameOrLastName(String firstName, String lastName);

    void update(Long id, User user);
}
