package com.softserverinc.edu.forms;

import com.softserverinc.edu.constants.PageConstant;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.services.IssueService;
import com.softserverinc.edu.services.WorkLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class WorkLogFormValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkLogFormValidator.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    public boolean validateWorklogUI(WorkLog workLog, User user, Long issueId) {
        return /*validateAmountOfTime(workLog) || */validateWorkingOnIssueDates(workLog, user, issueId);
    }

    public boolean validateAmountOfTime(WorkLog workLog) {
        if(!(workLog.getAmountOfTime() instanceof Long) || workLog.getAmountOfTime() <= 0)
            return false;
        try {
            SimpleDateFormat dateFormatSQL = new SimpleDateFormat(PageConstant.DATE_FORMAT);
            String startTime = workLogService.formatDate(workLog.getStartDate());
            String endTime = workLogService.formatDate(workLog.getEndDate());
            int days = (int) (1 + (dateFormatSQL.parse(endTime).getTime() -
                    dateFormatSQL.parse(startTime).getTime()) / PageConstant.MS_IN_ONE_DAY);
            double dailyAmountOfTime = workLog.getAmountOfTime() / days;
            //may specify user or project workday duration instead
            return dailyAmountOfTime < PageConstant.WORKDAY_DURATION_IN_HRS;
        } catch (ParseException e) {
            LOGGER.error(e.toString(), e);
        }
        return true;
    }

    public boolean validateWorkingOnIssueDates(WorkLog workLog, User user, Long issueId) {
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