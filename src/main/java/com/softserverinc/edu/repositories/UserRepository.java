package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContaining(String email);

    List<User> findByEmail(String email);

    User findByEmailIs(String email);

    List<User> findByRole(UserRole role);

    List<User> findByProjectAndIsDeletedAndEnabledIs(Project project, boolean isDeleted, int enabled);

    Page<User> findByProjectAndIsDeletedFalseAndEnabledIs(Project project, int enabled, Pageable pageable);

    Page<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName, Pageable pageable);

    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);

    Page<User> findByLastNameContaining(String lastName, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    Page<User> findByIsDeletedFalseAndEnabledIs(int enabled, Pageable pageable);

    Page<User> findByRole(UserRole role, Pageable pageable);

    Page<User> findByProjectAndFirstNameContainingAndRole(Project project, String searchedString, UserRole searchedRole,
                                                          Pageable pageable);

    Page<User> findByProjectAndLastNameContainingAndRole(Project project, String searchedString, UserRole searchedRole,
                                                         Pageable pageable);

    Page<User> findByEmailContainingAndRole(String searchedString, UserRole searchedRole, Pageable pageable);

    Page<User> findByProjectAndRole(Project project, UserRole role, Pageable pageable);

    User findByProjectAndRole(Project project, UserRole role);

    Page<User> findByProjectAndFirstNameContainingAndRoleNot(Project project, String searchedString, UserRole notRole,
                                                             Pageable pageable);

    Page<User> findByProjectAndLastNameContainingAndRoleNot(Project project, String searchedString, UserRole notRole,
                                                            Pageable pageable);

    Page<User> findByEmailContainingAndProjectAndRoleNot(String searchedString, Project project, UserRole notRole,
                                                         Pageable pageable);

    Page<User> findByProjectAndRoleNot(Project project, UserRole notRole, Pageable pageable);
}
