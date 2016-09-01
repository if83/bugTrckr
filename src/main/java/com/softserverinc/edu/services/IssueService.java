package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }

    public List<Issue> findByTitle(String title) {
        return issueRepository.findByTitle(title);
    }

    public List<Issue> findByType(IssueType type) {
        return issueRepository.findByType(type);
    }

    public List<Issue> findByStatus(IssueStatus status) {
        return issueRepository.findByStatus(status);
    }

    public List<Issue> findByPriority(IssuePriority priority) {
        return issueRepository.findByPriority(priority);
    }

    public List<Issue> findByProjectRelease(ProjectRelease projectRelease) {
        return issueRepository.findByProjectRelease(projectRelease);
    }

    public boolean isStatusChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        if (!(oldIssue.getStatus().equals(changedIssue.getStatus()))) {
            return true;
        }
        return false;
    }

    public boolean isAssigneeChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        if (!(oldIssue.getAssignee().equals(changedIssue.getAssignee()))) {
            return true;
        }
        return false;
    }

    public List<Issue> findByAssignee(User assignee) {
        return issueRepository.findByAssignee(assignee);
    }

    public List<Issue> findByLabels(Label label) {
        return issueRepository.findByLabels(label);
    }

    public List<Issue> findByCreateTime(Date createTime) {
        return issueRepository.findByCreateTime(createTime);
    }

    public List<Issue> findByDueDate(Date dueDate) {
        return issueRepository.findByDueDate(dueDate);
    }

    public List<Issue> findByLastUpdateDate(Date lastUpdateDate) {
        return issueRepository.findByLastUpdateDate(lastUpdateDate);
    }

    public List<Issue> findByEstimateTime(Date estimateTime) {
        return issueRepository.findByEstimateTime(estimateTime);
    }

    public Issue findByParentId(Long parentId) {
        return issueRepository.findByParentId(parentId);
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Transactional
    public Issue save(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

    @Transactional
    public void delete(Long id) {
        issueRepository.delete(id);
    }

    @Transactional
    public Issue update(Issue issue) {
        return issueRepository.saveAndFlush(issue);
    }

}
