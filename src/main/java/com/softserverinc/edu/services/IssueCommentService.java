package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.IssueComment;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.repositories.IssueCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IssueCommentService {

    @Autowired
    private IssueCommentRepository issueCommentRepository;

    public IssueComment findOne(Long id) {
        return issueCommentRepository.findOne(id);
    }

    public List<IssueComment> findByUser(User user) {
        return issueCommentRepository.findByUser(user);
    }

    public List<IssueComment> findByIssue(Issue issue) {
        return issueCommentRepository.findByIssue(issue);
    }

    public List<IssueComment> findByTimeStamp(Date timeStamp) {
        return issueCommentRepository.findByTimeStamp(timeStamp);
    }

    public List<IssueComment> findAll() {
        return issueCommentRepository.findAll();
    }

    @Transactional
    public IssueComment save(IssueComment issueComment) {
        return issueCommentRepository.saveAndFlush(issueComment);
    }

    @Transactional
    public void delete(Long id) {
        issueCommentRepository.delete(id);
    }

    @Transactional
    public IssueComment update(IssueComment issueComment) {
        return issueCommentRepository.saveAndFlush(issueComment);
    }

}