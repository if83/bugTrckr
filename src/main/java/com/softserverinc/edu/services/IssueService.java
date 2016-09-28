package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.IssuePriority;
import com.softserverinc.edu.entities.enums.IssueStatus;
import com.softserverinc.edu.entities.enums.IssueType;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.IssueRepository;
import com.softserverinc.edu.services.securityServices.IssueSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IssueCommentService issueCommentService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectReleaseService projectReleaseService;

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueSecurityService issueSecurityService;

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }

    public List<IssueStatus> getAvaliableStatusesForStatus(IssueStatus status) {
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

    @Transactional
    public Page<Issue> findByProjectRelease(ProjectRelease projectRelease, Pageable pageable) {
        return issueRepository.findByProjectRelease(projectRelease, pageable);
    }

    @Transactional
    public Page<Issue> findByReleaseAndIssueTitle(ProjectRelease projectRelease, String searchedString, Pageable pageable) {
        return issueRepository.findByProjectReleaseAndTitleContaining(projectRelease, searchedString, pageable);
    }

    @Transactional
    public Page<Issue> findIssuesByProject(Project project, String searchedString, Pageable pageable) {
        return issueRepository.findByProjectAndTitleContaining(project, searchedString, pageable);
    }

    public boolean isStatusChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        return !(oldIssue.getStatus().equals(changedIssue.getStatus()));
    }

    public boolean isAssigneeChanged(Issue changedIssue) {
        Issue oldIssue = findById(changedIssue.getId());
        return !(oldIssue.getAssignee().equals(changedIssue.getAssignee()));
    }

    public Page<Issue> findByAssignee(User assignee, Pageable pageable) {
        return issueRepository.findByAssignee(assignee, pageable);
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
    public Page<Issue> findByProject(Project project, Pageable pageable) {
        return issueRepository.findByProject(project, pageable);
    }

    public void populateDefaultModel(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("types", IssueType.values());
        model.addAttribute("priority", IssuePriority.values());
        model.addAttribute("allLabels", labelService.findAll());
        model.addAttribute("users", authenticationCheck());
        model.addAttribute("projectReleases", projectReleaseService.findAll());
    }

    public Page<Issue> findByUser(Principal principal, Pageable pageable) {
        return issueRepository.findByAssignee((userService.findByEmailIs(principal.getName())), pageable);
    }

    private List<User> authenticationCheck() {
        if (!issueSecurityService.isAuthenticated()) {
            return userService.findByRole(UserRole.ROLE_PROJECT_MANAGER);
        }
        return userService.findAll();
    }

    public void addAttributes(Issue issue, RedirectAttributes redirectAttributes){
        if (issue.isNewIssue()) {
            redirectAttributes.addFlashAttribute("msg", String.format("%s added successfully!", issue.getTitle()));
        } else {
            redirectAttributes.addFlashAttribute("msg", String.format("%s updated successfully!", issue.getTitle()));
        }
    }
}
