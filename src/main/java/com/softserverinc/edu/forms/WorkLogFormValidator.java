package com.softserverinc.edu.forms;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Define  methods for accepted from UI WorkLog instance
 */
@Component
public class WorkLogFormValidator {

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    /**
     * Invoke more specific validation methods
     *
     * <p>invoke {@link #validateWorkingOnIssueDates(WorkLog, User, Long)}</p>
     * <p>/invoke {@link #validateAmountOfTime(WorkLog)}</p>
     * @param workLog WorkLog instance accepted from UI
     * @param user current user
     * @param issueId issue's id
     * @return Boolean representation of validation
     */
    public boolean validateWorklogUI(WorkLog workLog, User user, Long issueId) {
        return validateAmountOfTime(workLog) && validateWorkingOnIssueDates(workLog, user, issueId);
    }

    /**
     * Validate daily amount of working time
     * Prevents logging more than workday duration amount of working time
     *
     * @param workLog WorkLog instance accepted from UI
     * @return Boolean representation of validation
     */
    private boolean validateAmountOfTime(WorkLog workLog){
        if(!(workLog.getAmountOfTime() instanceof Long) || workLog.getAmountOfTime() <= 0) {
            return false;
        }
        Long startTime = workLog.getStartDate().getTime();
        Long endTime = workLog.getEndDate().getTime();
        int days = (int) (1 + (endTime - startTime) / PageConstant.MS_IN_ONE_DAY);
        double dailyAmountOfTime = workLog.getAmountOfTime() / days;
        //may specify user or project workday duration instead
        return dailyAmountOfTime <= PageConstant.WORKDAY_DURATION_IN_HRS;
    }

    /**
     * Validate working on issue dates
     * Prevents same periods repetitive work logging by current user and logging periods outside issue's lifecycle
     *
     * @param workLog WorkLog instance accepted from UI
     * @param user current user
     * @param issueId issue's id
     * @return Boolean representation of validation
     */
    private boolean validateWorkingOnIssueDates(WorkLog workLog, User user, Long issueId) {
        Long startTimeUI = workLog.getStartDate().getTime();
        Long endTimeUI = workLog.getEndDate().getTime();
        Long issueDueDateTime = workLog.getIssue().getDueDate().getTime();
        Long issueCreateDateTime = workLog.getIssue().getCreateTime().getTime();

        if (endTimeUI < startTimeUI || issueDueDateTime < endTimeUI || issueCreateDateTime > startTimeUI) {
            return false;
        }

        if (workLog.getId() == null) {
            List<WorkLog> workLoglist = workLogService.findByUserAndIssue(user, issueService.findById(issueId));
            for (WorkLog workLogIterator : workLoglist) {
                Long startTimeDB = workLogIterator.getStartDate().getTime();
                Long endTimeDB = workLogIterator.getEndDate().getTime();
                Boolean wasStartTimeLogged = startTimeUI >=  startTimeDB && startTimeUI <= endTimeDB;
                Boolean wasEndTimeLogged = endTimeUI >= startTimeDB && endTimeUI <= endTimeDB;
                Boolean wasPeriodLogged = startTimeUI <= startTimeDB && endTimeUI >= endTimeDB;

                if (wasStartTimeLogged || wasEndTimeLogged || wasPeriodLogged) {
                    return false;
                }
            }
        }
        return true;
    }
}