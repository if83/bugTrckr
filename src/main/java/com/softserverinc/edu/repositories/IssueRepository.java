package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contain custom Spring Data JPA methods wor working with DB
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    /**
     * Pageable list of Issue entities by specified title
     *
     * @param title    represents title for search
     * @param pageable Pageable object
     * @return Pageable list of Issue entities by specified title
     */
    Page<Issue> findByTitleContaining(String title, Pageable pageable);

    /**
     * Pageable list of Issue entities by specified release
     *
     * @param projectRelease represents projectRelease instance
     * @param pageable       Pageable object
     * @return Pageable list of Issue entities by specified release
     */
    Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable);

    /**
     * Pageable list of Issue entities by specified release and searched title
     *
     * @param projectRelease represents projectRelease instance
     * @param title          represents title for search
     * @param pageable       Pageable object
     * @return Pageable list of Issue entities by specified release and searched title
     */
    Page<Issue> findByProjectReleaseAndTitleContaining(ProjectRelease projectRelease, String title, Pageable pageable);

    /**
     * Pageable list of Issue entities by specified assignee
     *
     * @param assignee represents specified assignee
     * @param pageable Pageable object
     * @return Pageable list of Issue entities by specified assignee
     */
    Page<Issue> findByAssignee(User assignee, Pageable pageable);

    /**
     * Pageable list of all Issue entities
     *
     * @param pageable Pageable object
     * @return Pageable list of all Issue entities
     */
    Page<Issue> findAll(Pageable pageable);

    /**
     * Pageable list of Issue entities by specified project
     *
     * @param project  represents project instance
     * @param pageable Pageable object
     * @return Pageable list of Issue entities by specified project
     */
    Page<Issue> findByProject(Project project, Pageable pageable);

    /**
     * Pageable list of Issue entities by specified release and searched String
     *
     * @param project        represents project instance
     * @param searchedString represents String for search
     * @param pageable       Pageable object
     * @return Pageable list of Issue entities by specified release and searched String
     */
    Page<Issue> findByProjectAndTitleContaining(Project project, String searchedString, Pageable pageable);
}
