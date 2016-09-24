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

    User findByProjectAndRoleIsAndIsDeleted(Project project, UserRole role, boolean isDeleted);

    List<User> findByProjectAndIsDeletedAndEnabledIs(Project project, boolean isDeleted, int enabled);

    Page<User> findByProjectAndRoleNotAndIsDeletedAndEnabledIs(Project project, UserRole role,boolean isDeleted,
                                                            int enabled, Pageable pageable);

    Page<User> findByProjectAndIsDeletedFalseAndEnabledIs(Project project, int enabled, Pageable pageable);

    List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String lastName);

    Page<User> findAll(Pageable pageable);

    Page<User> findByIsDeletedFalseAndEnabledIs(int enabled, Pageable pageable);

    Page<User> findByRoleAndIsDeletedAndEnabledIs(UserRole role, boolean isDeleted, int enabled, Pageable pageable);

    Page<User> findByProjectAndFirstNameContainingAndRoleAndIsDeletedAndEnabledIs(Project project, String firstName,
                                                                                  UserRole role, boolean isDeleted,
                                                                                  int enabled, Pageable pageable);

    Page<User> findByProjectAndLastNameContainingAndRoleAndIsDeletedAndEnabledIs(Project project, String lastName,
                                                                              UserRole role, boolean isDeleted,
                                                                              int enabled, Pageable pageable);
    Page<User> findByProjectAndRoleAndIsDeletedAndEnabledIs(Project project, UserRole role, boolean isDeleted,
                                                            int enable, Pageable pageable);

    Page<User> findByProjectAndFirstNameContainingAndIsDeletedAndEnabledIs(Project project, String firstName,
                                                                           boolean isDeleted, int enabled,
                                                                           Pageable pageable);

    Page<User> findByProjectAndLastNameContainingAndIsDeletedAndEnabledIs(Project project, String lastName,
                                                                          boolean isDeleted, int enabled,
                                                                          Pageable pageable);

    Page<User> findByEmailContainingAndRoleAndIsDeletedAndEnabledIs(String email, UserRole roleUser,
                                                                    boolean isDeleted, int enable, Pageable pageable);
}
