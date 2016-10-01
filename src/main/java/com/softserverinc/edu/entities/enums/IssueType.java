package com.softserverinc.edu.entities.enums;

public enum IssueType {
    TASK("Task"), BUG("Bug"), IMPROVEMENT("Improvement"), EPIC("Epic");

    private String status;

    IssueType(String role) {
        this.status = role;
    }

    @Override
    public String toString() {
        return status;
    }
}
