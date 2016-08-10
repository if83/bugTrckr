package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
}
