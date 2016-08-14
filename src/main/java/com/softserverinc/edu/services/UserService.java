package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;

import java.util.List;

public interface UserService {

    User findOne(Long id);

    List<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByProjectId(Project projectId);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

    User update(User user);
}
