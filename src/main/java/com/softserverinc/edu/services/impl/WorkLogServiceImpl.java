package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.WorkLogRepository;
import com.softserverinc.edu.services.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    @Autowired
    WorkLogRepository workLogRepository;

    @Override
    public WorkLog findOne(Long id) {
        return workLogRepository.findOne(id);
    }

    @Override
    public List<WorkLog> findByUser(User user) {
        return workLogRepository.findByUser(user);
    }

    @Override
    public List<WorkLog> findByIssue(Issue issue) {
        return workLogRepository.findByIssue(issue);
    }

    @Override
    public List<WorkLog> findByTime(Date time) {
        return workLogRepository.findByTime(time);
    }

    @Override
    public List<WorkLog> findByAmount(Long amount) {
        return workLogRepository.findByAmount(amount);
    }

    @Override
    public List<WorkLog> findByIsDeleted(Boolean isDeleted) {
        return workLogRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<WorkLog> findAll() {
        return workLogRepository.findAll();
    }

    @Override
    @Transactional
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        workLogRepository.findOne(id).setIsDeleted(true);
    }

    @Override
    @Transactional
    public WorkLog update(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

}
