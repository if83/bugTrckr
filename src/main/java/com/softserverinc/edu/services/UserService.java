package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findOne(Long id);

    List<User> findByEmailContaining(String email);

    List<User> findByRole(UserRole role);

    List<User> findByProject(Project project);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String lastName);

    List<User> findByIsDeleted(Boolean isDeleted);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

    User update(User user);

    Page<User> findAll(Pageable pageable);

    Long count();
}
