package com.softserverinc.edu.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class LoginForm {

    @NotEmpty(message = "Please enter email")
    @Email(message = "Please enter valid email")
    private String username;

    @Min(value = 5, message = "Password must be at least 5 characters")
    @Max(value = 15, message = "Password must be not longer than 15 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}