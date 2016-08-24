package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    public WorkLog findOne(Long id) {
        return workLogRepository.findOne(id);
    }

    public List<WorkLog> findByUser(User user) {
        return workLogRepository.findByUser(user);
    }

    public List<WorkLog> findByIssue(Issue issue) {
        return workLogRepository.findByIssue(issue);
    }

    public List<WorkLog> findByStartTime(Date startTime) {
        return workLogRepository.findByStartTime(startTime);
    }

    public List<WorkLog> findByAmountOfTime(Long amountOfTime) {
        return workLogRepository.findByAmountOfTime(amountOfTime);
    }

    public List<WorkLog> findByIsDeleted(Boolean isDeleted) {
        return workLogRepository.findByIsDeleted(isDeleted);
    }

    public List<WorkLog> findAll() {
        return workLogRepository.findAll();
    }

    @Transactional
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    @Transactional
    public void delete(Long id) {
        workLogRepository.delete(id);
    }

    @Transactional
    public WorkLog update(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

}
