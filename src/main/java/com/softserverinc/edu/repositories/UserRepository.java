package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
