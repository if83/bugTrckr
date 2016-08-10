package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
