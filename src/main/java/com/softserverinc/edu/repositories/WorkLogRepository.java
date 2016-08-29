package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findByUser(User user);

    List<WorkLog> findByIssue(Issue issue);

    List<WorkLog> findByStartTime(Date startTime);

    List<WorkLog> findByAmountOfTime(Long amountOfTime);

}
