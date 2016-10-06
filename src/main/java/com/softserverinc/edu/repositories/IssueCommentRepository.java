package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contain custom Spring Data JPA methods wor working with DB
 */
@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {

    /**
     * List of IssueComment entries by specified issue
     * @param issue
     * @return List of IssueComment entries by specified issue
     */
    List<IssueComment> findByIssue(Issue issue);
}
