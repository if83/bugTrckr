package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.*;
import com.softserverinc.edu.entities.enums.*;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.repositories.WorkLogRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WorklogServiceTest {

    @Mock
    private WorkLogRepository workLogRepository;

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private WorkLogService workLogService;

    private Long id;
    private WorkLog workLog;
    private List<WorkLog> workLogList;
    private Page<WorkLog> workLogPage;
    private User user;
    private Issue issue;
    private Pageable pageable;

    @Test
    public void testFindOne() throws Exception {
        id = 1L;
        workLog = getTestWorklog(id);
        Mockito.when(workLogRepository.findOne(id)).thenReturn(workLog);

        WorkLog currentWorkLog = workLogService.findOne(id);
        Assert.assertEquals(workLog, currentWorkLog);
    }

    @Test
    public void testFindByUserAndIssue() throws Exception {
        id = 1L;
        issue = getTestIssue(id);
        user = getTestUser(id);
        Mockito.when(workLogRepository.findByUserAndIssue(user,issue)).thenReturn(workLogList);

        List<WorkLog> currentWorkLogList = workLogService.findByUserAndIssue(user, issue);
        Assert.assertEquals(workLogList, currentWorkLogList);
    }

    @Test
    public void testFindByIssue() {
        id = 1L;
        issue = getTestIssue(id);
        Mockito.when(workLogRepository.findByIssue(issue)).thenReturn(workLogList);

        List<WorkLog> currentWorkLogList = workLogService.findByIssue(issue);
        Assert.assertEquals(workLogList, currentWorkLogList);
    }

    @Test
    public void testFindByIssuePageable() {
        id = 1L;
        issue = getTestIssue(id);
        workLogPage = getTestWorkLogPage(id);
        Mockito.when(workLogRepository.findByIssue(issue, pageable)).thenReturn(workLogPage);

        Page<WorkLog> currentWorkLogPage = workLogService.findByIssue(issue, pageable);
        Assert.assertEquals(workLogPage, currentWorkLogPage);
    }

    @Test
    public void testSave() throws Exception {
        id = 1L;
        workLog = getTestWorklog(id);
        Mockito.when(workLogRepository.findOne(0L)).thenReturn(workLog);
        workLogService.save(workLog);
        Mockito.verify(workLogRepository).saveAndFlush(workLog);
    }

    @Test
    public void testDelete() throws Exception {
        id = 1L;
        workLog = getTestWorklog(id);
        Mockito.when(workLogRepository.findOne(0L)).thenReturn(workLog);
        workLogService.delete(id);
        Mockito.verify(workLogRepository).delete(id);
    }

    private WorkLog getTestWorklog(Long id) {
        WorkLog workLog = new WorkLog();
        workLog.setId(id);
        workLog.setIssue(new Issue());
        workLog.setUser(getTestUser(1L));
        workLog.setStartDate(new Date());
        workLog.setEndDate(new Date());
        workLog.setAmountOfTime(6L);
        return workLog;
    }

    private Issue getTestIssue(Long id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setTitle("Issue");
        issue.setType(IssueType.BUG);
        issue.setDescription("Test");
        issue.setPriority(IssuePriority.BLOCKER);
        issue.setStatus(IssueStatus.OPEN);
        issue.setCreatedBy(new User());
        issue.setAssignee(new User());
        issue.setCreateTime(new Date());
        issue.setDueDate(new Date());
        issue.setProject(getTestProject(id));
        issue.setProjectRelease(getTestRelease(id));
        issue.setLastUpdateDate(new Date());
        return issue;
    }

    private User getTestUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setFirstName("manager");
        user.setLastName("manager");
        user.setEmail("manager@ss.com");
        user.setPassword("manager");
        user.setRole(UserRole.ROLE_PROJECT_MANAGER);
        user.setDescription("manager");
        user.setEnabled(1);
        user.setProject(getTestProject(id));
        user.setIsDeleted(false);
        return user;
    }

    private Project getTestProject(Long id) {
        Project project = new Project();
        project.setId(id);
        project.setTitle("Project");
        project.setGuestAddComment(true);
        project.setGuestCreateIssues(true);
        project.setGuestView(true);
        project.setDescription("Description");
        return project;
    }

    private ProjectRelease getTestRelease(Long id) {
        ProjectRelease projectRelease = new ProjectRelease();
        projectRelease.setId(id);
        projectRelease.setVersion("Version");
        projectRelease.setReleaseStatus(ReleaseStatus.OPEN);
        return projectRelease;
    }

    private Page<WorkLog> getTestWorkLogPage(Long id) {
        WorkLog workLog = getTestWorklog(id);
        List<WorkLog> workLogs = new ArrayList<>();
        workLogs.add(workLog);
        return new PageImpl<>(workLogs);
    }
}