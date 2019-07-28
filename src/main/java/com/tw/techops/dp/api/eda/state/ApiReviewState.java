package com.tw.techops.dp.api.eda.state;

public enum ApiReviewState {
    INTIAL("initial", "INITIAL_TEXT"),
    NEW("new", "NEW_TEXT"),
    REVIEWING("reviewing", "REVIEWING_TEXT"),
    APPROVED("approved", "APPROVED_TEXT"),
    REJECTED("rejected", "REJECTED_TEXT");

    private final String name;
    private final String value;

    ApiReviewState(String stateName, String stateValue) {
        this.name = stateName;
        this.value = stateValue;
    }
}
