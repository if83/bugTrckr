package com.softserverinc.edu.entities.enums;

public enum UserRole {
    ROLE_ADMIN("Admin"), ROLE_PROJECT_MANAGER("Project Manager"), ROLE_DEVELOPER("Developer"), ROLE_QA("QA"),
    ROLE_USER("User"), ROLE_ANONYMOUS("Guest");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this == ROLE_ADMIN;
    }

    public boolean isProjectManager() {
        return this == ROLE_PROJECT_MANAGER;
    }

    public boolean isDeveloper() {
        return this == ROLE_DEVELOPER;
    }

    public boolean isQA() {
        return this == ROLE_QA;
    }

    public boolean isUser() {
        return this == ROLE_USER;
    }

    public boolean isGuest() {
        return this == ROLE_ANONYMOUS;
    }

    @Override
    public String toString() {
        return role;
    }
}
