package com.softserverinc.edu.forms;

import com.softserverinc.edu.entities.Issue;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class IssueFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Issue.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Issue issue = (Issue) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.issue_form.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty.issue_form.type");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "NotEmpty.issue_form.priority");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "NotEmpty.issue_form.status");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "editAbility", "NotEmpty.issue_form.editAbility");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.issue_form.description");

        if (issue.getTitle().length() > 32) {
            errors.rejectValue("title", "Maxsize.issue_form.stringlength");
        }

    }
}