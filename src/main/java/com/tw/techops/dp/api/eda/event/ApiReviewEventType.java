package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.state.ApiReviewState;

public enum ApiReviewEventType {
    CREATE(ApiReviewState.INTIAL, ApiReviewState.NEW),
    START(ApiReviewState.NEW, ApiReviewState.REVIEWING),
    REJECT(ApiReviewState.REVIEWING, ApiReviewState.REJECTED),
    APPROVE(ApiReviewState.REVIEWING, ApiReviewState.APPROVED);

    private final ApiReviewState sourceState;
    private final ApiReviewState targetState;

    ApiReviewEventType(ApiReviewState sourceState, ApiReviewState targetState) {
        this.sourceState = sourceState;
        this.targetState = targetState;
    }

    public ApiReviewState getSourceState(){
        return sourceState;
    }

    public ApiReviewState getTargetState(){
        return targetState;
    }
}
