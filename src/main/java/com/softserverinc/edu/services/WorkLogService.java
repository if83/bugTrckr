package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.WorkLog;

import java.util.List;

public interface WorkLogService {

    WorkLog getOne(Long id);
    WorkLog save(WorkLog workLog);
    void delete(Long id);
    WorkLog update(WorkLog workLog);
    List<WorkLog> getAll();

}
