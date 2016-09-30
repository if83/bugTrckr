package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByIssue(Issue issue);

    Page<History> findByAssignedToUserIdOrChangedByUserIdOrderByCreateTimeDesc(Long assignedToUserId, Long ChangedByUserId, Pageable pageable);

    Page<History> findByChangedByUserIdAndIssueCommentIsNotNull(Long ChangedByUserId, Pageable pageable);

    Page<History> findByIssueOrderByCreateTimeDesc(Issue issue, Pageable pageable);

}
