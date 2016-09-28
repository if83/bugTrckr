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
import java.util.Date;
import java.util.List;

@Component
public class WorkLogFormValidator {

    public static final Logger LOGGER = LoggerFactory.getLogger(WorkLogFormValidator.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private IssueService issueService;

    public boolean validateAmountOfTime(WorkLog workLog) {
        if(!(workLog.getAmountOfTime() instanceof Long) || workLog.getAmountOfTime() < 0)
            return false;
        try {
            SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
            String startTime = workLogService.parseDateToSQLFormat(workLog.getStartDate());
            String endTime = workLogService.parseDateToSQLFormat(workLog.getEndDate());
            int days = (int) (1 + (dateFormatSQL.parse(endTime).getTime() -
                    dateFormatSQL.parse(startTime).getTime()) / PageConstant.MS_IN_ONE_DAY);
            double dailyAmountOfTime = workLog.getAmountOfTime() / days;
            return dailyAmountOfTime < PageConstant.WORKDAY_DURATION_IN_HRS; //may specify user or project workday duration instead
        } catch (ParseException e) {
            LOGGER.error(e.toString(), e);
        }
        return false;
    }

    public boolean validateWorkingOnIssueDates(WorkLog workLog, User user, Long issueId) {
        Date startTime = workLog.getStartDate();
        Date endTime = workLog.getEndDate();
        if (endTime.getTime() < startTime.getTime() ||
                workLog.getIssue().getDueDate().getTime() < endTime.getTime() ||
                workLog.getIssue().getCreateTime().getTime() > startTime.getTime()) {
            return false;
        }
        if (workLog.getId() == null) {
            List<WorkLog> workLoglist = workLogService.findByUserAndIssue(user, issueService.findById(issueId));
            for (WorkLog workLogIterator : workLoglist) {
                if ((workLog.getStartDate().getTime() >= workLogIterator.getStartDate().getTime() &&
                        workLog.getStartDate().getTime() <= workLogIterator.getEndDate().getTime()) ||
                        (workLog.getEndDate().getTime() >= workLogIterator.getStartDate().getTime() &&
                                workLog.getEndDate().getTime() <= workLogIterator.getEndDate().getTime()) ||
                        (workLog.getStartDate().getTime() <= workLogIterator.getStartDate().getTime() &&
                                workLog.getEndDate().getTime() >= workLogIterator.getEndDate().getTime())) {
                    return false;
                }
            }
        }
        return true;
    }
}