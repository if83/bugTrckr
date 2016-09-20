package com.softserverinc.edu.entities.enums;

public enum IssueStatus {
    OPEN("Open"), IN_PROGRESS("In progress"), QA_VALIDATION("QA validation"), RESOLVED("Resolved"), INVALID("Invalid");

    private String status;

    IssueStatus(String role) {
        this.status = role;
    }

    @Override
    public String toString() {
        return status;
    }
}
