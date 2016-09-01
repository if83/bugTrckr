package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IssueCommentRepository  extends JpaRepository<IssueComment, Long> {

    List<IssueComment> findByUser(User user);

    List<IssueComment> findByIssue(Issue issue);

    List<IssueComment> findByTimeStamp(Date timeStamp);

}
