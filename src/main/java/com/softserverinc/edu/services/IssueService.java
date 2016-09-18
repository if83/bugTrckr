package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<IssueStatus> getAvaliableIssueStatusesForStatus(IssueStatus status) {
        List<IssueStatus> result = new ArrayList<>();
        switch (status) {
            case OPEN:
                result.add(IssueStatus.IN_PROGRESS);
                result.add(IssueStatus.INVALID);
                return result;
            case IN_PROGRESS:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.QA_VALIDATION);
                result.add(IssueStatus.INVALID);
                return result;
            case INVALID:
                result.add(IssueStatus.OPEN);
                return result;
            case QA_VALIDATION:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.RESOLVED);
                return result;
            case RESOLVED:
                result.add(IssueStatus.OPEN);
                result.add(IssueStatus.INVALID);
                return result;
            default:
                return result;
        }
    }

    public List<Issue> findByProjectRelease(ProjectRelease projectRelease) {
        return issueRepository.findByProjectRelease(projectRelease);
    }

    public Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable) {
        return issueRepository.findByProjectRelease(projectRelease, pageable);
    }

    public Page<Issue> findByProjectReleaseAndTitleContaining(ProjectRelease projectRelease, String searchedString, Pageable pageable) {
        return issueRepository.findByProjectReleaseAndTitleContaining(projectRelease, searchedString, pageable);
    }

    public boolean isStatusChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        return !(oldIssue.getStatus().equals(changedIssue.getStatus()));
    }

    public boolean isAssigneeChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        return !(oldIssue.getAssignee().equals(changedIssue.getAssignee()));
    }

    public List<Issue> findByAssignee(User assignee) {
        return issueRepository.findByAssignee(assignee);
    }

    public List<Issue> findByAssignee(User assignee, Pageable pageable) {
        return issueRepository.findByAssignee(assignee, pageable);
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

    public Page<Issue> findByTitleContaining(String title, Pageable pageable) {
        return issueRepository.findByTitleContaining(title, pageable);
    }

    public Page<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable);
    }

    @Transactional
    public Page<Issue> findByProjectId(Long projectId, Pageable pageable) {
        return issueRepository.findByProjectId(projectId, pageable);
    }
}
