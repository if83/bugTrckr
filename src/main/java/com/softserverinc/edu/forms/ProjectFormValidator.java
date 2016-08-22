package com.softserverinc.edu.forms;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProjectFormValidator implements Validator {

    @Autowired
    ProjectService projectService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.projectForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.projectForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "guestView", "NotEmpty.projectForm.guestView");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "guestCreateIssues", "NotEmpty.projectForm.guestCreateIssues");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "guestAddComment", "NotEmpty.projectForm.guestAddComment");

        if (project.getTitle().length() > 100) {
            errors.rejectValue("title", "Maxsize.title.stringlength");
        }

        if (project.getTitle().length() > 10000) {
            errors.rejectValue("description", "Maxsize.description.stringlength");
        }
    }
}