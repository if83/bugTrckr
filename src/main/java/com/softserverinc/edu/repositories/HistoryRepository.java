package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByIssue(Issue issue);

    List<History> findByAssignedToUser(User assignedToUser);

    List<History> findByChangedByUser(User changedByUser);

    List<History> findByAssignedToUserOrChangedByUserOrderByCreateDateDesc(User assignedToUser, User ChangedByUser);

}
