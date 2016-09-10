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

    List<User> findByRoleNotAndIsDeletedFalse(UserRole role);

    List<User> findByProjectAndIsDeleted(Project project, boolean isDeleted);

    List<User> findByProject(Project project);

    List<User> findByProjectAndIsDeletedAndEnabledIs(Project project, boolean isDeleted, int enabled);

    Page<User> findByProjectAndIsDeletedFalseAndEnabledIs(Project project, int enabled, Pageable pageable);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String lastName);

    Page<User> findAll(Pageable pageable);

    Page<User> findByIsDeletedFalseAndEnabledIs(int enabled, Pageable pageable);

    Page<User> findByEmailAndRoleAndIsDeletedAndEnabledIs(String email, UserRole role, boolean isDeleted,
                                                                 int enable, Pageable pageable);

    Page<User> findByRoleAndIsDeletedAndEnabledIs(UserRole role, boolean isDeleted, int enabled, Pageable pageable);

    Page<User> findByFirstNameContainingAndRoleAndIsDeletedAndEnabledIs(String firstName,  UserRole role,
                                                                        boolean isDeleted, int enabled,
                                                                        Pageable pageable);

    Page<User> findByLastNameContainingAndRoleAndIsDeletedAndEnabledIs(String lastName,  UserRole role,
                                                                       boolean isDeleted, int enabled,
                                                                       Pageable pageable);
}
