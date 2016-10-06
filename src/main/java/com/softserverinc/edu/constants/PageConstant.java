package com.softserverinc.edu.constants;

import java.util.concurrent.TimeUnit;

public interface PageConstant {

    int AMOUNT_PROJECT_ELEMENTS = 12;
    int AMOUNT_ISSUE_ELEMENTS = 20;
    int WORKDAY_DURATION_IN_HRS = 8;
    int MS_IN_ONE_DAY = (int) TimeUnit.DAYS.toMillis(1);
    String DATE_FORMAT = "dd.MM.yyyy";
    String ISSUE_DATE_FORMAT = "dd/MM/yyyy";
    String EMPTY_STRING = "";
    int MIN_ANONYM_NAME_LENGTH = 8;
    int MAX_ANONYM_NAME_LENGTH = 32;

}
