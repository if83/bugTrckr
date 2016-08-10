package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}
