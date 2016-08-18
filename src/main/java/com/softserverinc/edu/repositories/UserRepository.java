package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    List<User> findByEmailContaining(String email);

    List<User> findByRole(UserRole role);

    List<User> findByProject(Project project);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String lastName);

    List<User> findByIsDeleted(Boolean isDeleted);

}
