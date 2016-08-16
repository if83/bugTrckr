package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;

import java.util.Date;
import java.util.List;

public interface WorkLogService {

    WorkLog findOne(Long id);

    List<WorkLog> findByUser(User user);

    List<WorkLog> findByIssue(Issue issue);

    List<WorkLog> findByStartTime(Date startTime);

    List<WorkLog> findByAmountOfTime(Long amountOfTime);

    List<WorkLog> findByIsDeleted(Boolean isDeleted);

    List<WorkLog> findAll();

    WorkLog save(WorkLog workLog);

    void delete(Long id);

    WorkLog update(WorkLog workLog);

}
