package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
