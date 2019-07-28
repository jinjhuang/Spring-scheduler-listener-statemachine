package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.state.ApiReviewState;

public enum ApiReviewStateChangeType {
    CREATE(ApiReviewState.INTIAL, ApiReviewState.NEW,null),
    START(ApiReviewState.NEW, ApiReviewState.REVIEWING,null),
    REJECT(ApiReviewState.REVIEWING, ApiReviewState.REJECTED, ApiStateChangeType.REJECT),
    APPROVE(ApiReviewState.REVIEWING, ApiReviewState.APPROVED, ApiStateChangeType.PUBLISH);

    private final ApiReviewState sourceState;
    private final ApiReviewState targetState;
    private final ApiStateChangeType apiStateEvent;

    ApiReviewStateChangeType(ApiReviewState sourceState, ApiReviewState targetState,ApiStateChangeType apiStateEvent) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.apiStateEvent = apiStateEvent;
    }

    public ApiReviewState getSourceState(){
        return sourceState;
    }

    public ApiReviewState getTargetState(){
        return targetState;
    }

    public ApiStateChangeType getApiStateEvent(){
        return apiStateEvent;
    }
}
