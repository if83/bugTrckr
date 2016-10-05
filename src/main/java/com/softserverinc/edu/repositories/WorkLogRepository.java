package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Contain custom Spring Data JPA methods wor working with DB
 */
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    /**
     * List of WorkLog entries by specified issue and user
     *
     * @param user  User instance
     * @param issue Issue instance
     * @return List of WorkLog entries by specified issue and user
     */
    List<WorkLog> findByUserAndIssue(User user, Issue issue);

    /**
     * List of WorkLog entries by specified issue
     *
     * @param issue Issue instance
     * @return List of WorkLog entries by specified issue
     */
    List<WorkLog> findByIssue(Issue issue);

    /**
     * Pageable list of WorkLog entries by specified issue
     *
     * @param issue Issue instance
     * @param pageable Pageable object
     * @return Pageable list of WorkLog entries by specified issue
     */
    Page<WorkLog> findByIssue(Issue issue, Pageable pageable);

    /**
     * Pageable list of all WorkLog entries
     *
     * @param pageable Pageable object
     * @return Pageable list of all WorkLog entries
     */
    Page<WorkLog> findAll(Pageable pageable);
}