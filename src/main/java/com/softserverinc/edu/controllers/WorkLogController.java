package com.softserverinc.edu.controllers;

import com.softserverinc.edu.entities.WorkLog;
import com.softserverinc.edu.forms.WorkLogFormValidator;
import com.softserverinc.edu.services.WorkLogService;
import com.softserverinc.edu.services.securityServices.WorkLogSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Serve requests used for working with WorkLog entity
 */
@Controller
public class WorkLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkLogController.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private WorkLogFormValidator workLogFormValidator;

    @Autowired
    private WorkLogSecurityService workLogSecurityService;

    /**
     * Serve WorkLog saving requests
     *
     * @param issueId issue's id
     * @param workLog instance of WorkLog accepted from UI
     * @param result contains workLog validating results
     * @param redirectAttributes attributes for redirect requests
     * @return redirecting URL to previous page
     */
    @PreAuthorize("@workLogSecurityService.hasPermissionToSaveWorkLog(#issueId, #workLog)")
    @RequestMapping(value = "issue/{issueId}/worklog/save", method = RequestMethod.POST)
    public String addWorkLogPOST(@PathVariable @P("issueId") Long issueId,
                                 @ModelAttribute("worklog") @P("workLog") @Valid WorkLog workLog,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || !workLogFormValidator.validateWorklogUI(workLog,
                workLogSecurityService.getActiveUser(), issueId)) {
            redirectAttributes.addFlashAttribute("msg", "Unable to save work log. Please fix your data.");
            return "redirect:/issue/" + issueId;
        }
        workLogService.save(workLog);
        LOGGER.info("Worklog saved, id= " + workLog.getId());
        redirectAttributes.addFlashAttribute("msg", "Successfully saved work log.");
        return "redirect:/issue/" + issueId;
    }

    /**
     * Serve WorkLog removing requests
     *
     * @param worklogId WorkLog entry's id
     * @param redirectAttributes attributes for redirect requests
     * @return redirecting URL to previous page
     */
    @PreAuthorize("@workLogSecurityService.hasPermissionToRemoveWorkLog(#worklogId)")
    @RequestMapping(value = "issue/{issueId}/worklog/{worklogId}/remove", method = RequestMethod.GET)
    public String removeWorkLog(@PathVariable @P("worklogId") Long worklogId,
                                RedirectAttributes redirectAttributes) {
        workLogService.delete(worklogId);
        LOGGER.debug("Worklog " + worklogId + " is removed!");
        redirectAttributes.addFlashAttribute("msg", "Work log entry has been deleted.");
        return "redirect:/issue/{issueId}";
    }
}