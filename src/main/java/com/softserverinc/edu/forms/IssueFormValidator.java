package com.softserverinc.edu.forms;

import com.softserverinc.edu.entities.Issue;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class IssueFormValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return Issue.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Issue issue = (Issue) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.issueform.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty.issueform.type");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "NotEmpty.issueform.priority");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "NotEmpty.issueform.status");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "editAbility", "NotEmpty.issueform.editAbility");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.issueform.description");

        if (issue.getTitle().length() > 32) {
            errors.rejectValue("title", "Maxsize.issueform.stringlength");
        }

    }
}