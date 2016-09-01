package com.softserverinc.edu.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class LoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Please enter your username addresss.")
    @Email
    private String username;

    @NotEmpty(message = "Please enter your password.")
    @Size(min = 5, max = 15, message = "Your password must between 6 and 15 characters")
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
