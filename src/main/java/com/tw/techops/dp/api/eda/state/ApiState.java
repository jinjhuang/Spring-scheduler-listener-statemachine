package com.tw.techops.dp.api.eda.state;

public enum ApiState {
    INITIAL("initial", "INITIAL_TEXT"),
    UNPUBLISHED("unpublished", "UNPUBLISHED_TEXT"),
    COMMITTED("committed", "COMMITTED_TEXT"),
    PUBLISHED("published", "PUBLISHED_TEXT"),
    REJECTED("rejected", "REJECTED_TEXT"),
    DEPRECATED("deprecated", "DEPRECATED_TEXT"),
    ARCHIVED("archived", "ARCHIVED_TEXT");

    private final String name;
    private final String value;

    ApiState(String stateName, String stateValue) {
        this.name = stateName;
        this.value = stateValue;
    }
}
