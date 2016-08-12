package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email =: email")
    User getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.role =: role")
    List<User> getListOfUsersByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.firstName =: firstName OR u.lastName =: lastName")
    List<User> getListOfUsersByFirstNameOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.firstName =: firstName, u.lastName =: lastName, u.email =: email," +
            " u.password =: password, u.role =: role, u.description =: description WHERE u.id =: id")
    public void update(@Param("id") Long id,
                       @Param("firstName") String firstName,
                       @Param("lastName") String lastName,
                       @Param("email") String email,
                       @Param("password") String password,
                       @Param("role") UserRole role,
                       @Param("description") String description);

}
