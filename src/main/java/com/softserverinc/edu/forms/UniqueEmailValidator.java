package com.softserverinc.edu.forms;

import com.softserverinc.edu.annotations.UniqueEmail;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findByEmailIs(email) == null;
    }
}