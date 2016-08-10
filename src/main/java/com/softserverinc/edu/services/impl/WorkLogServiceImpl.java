package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.repositories.WorkLogRepository;
import com.softserverinc.edu.services.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    @Autowired
    WorkLogRepository workLogRepository;

    @Override
    public WorkLog getOne(Long id) {
        return workLogRepository.getOne(id);
    }

    @Override
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    @Override
    public void delete(Long id) {
        workLogRepository.delete(id);
    }

    @Override
    public WorkLog update(WorkLog workLog) {
        return workLogRepository.saveAndFlush(workLog);
    }

    @Override
    public List<WorkLog> getAll() {
        return workLogRepository.findAll();
    }
}
