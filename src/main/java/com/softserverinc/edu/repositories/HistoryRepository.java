package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByIssue(Issue issue);

    List<History> findByAssignedToUserId(Long assignedToUserId);

    List<History> findByChangedByUserId(Long changedByUserId);

    List<History> findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(Long assignedToUserId, Long ChangedByUserId);

}
