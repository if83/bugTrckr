package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findByUserAndIssue(User user, Issue issue);

    List<WorkLog> findByIssue(Issue issue);

    Page<WorkLog> findByIssue(Issue issue, Pageable pageable);

    Page<WorkLog> findAll(Pageable pageable);

}