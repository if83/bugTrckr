package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
