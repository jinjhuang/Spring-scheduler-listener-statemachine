package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.state.ApiState;

public enum ApiStateChangeType {
    CREATE(ApiState.INITIAL, ApiState.UNPUBLISHED, null),
    COMMIT(ApiState.UNPUBLISHED, ApiState.COMMITTED, ApiReviewStateChangeType.CREATE),
    REJECT(ApiState.COMMITTED, ApiState.REJECTED, null),
    PUBLISH(ApiState.COMMITTED, ApiState.PUBLISHED, null),
    REOPEN(ApiState.REJECTED, ApiState.UNPUBLISHED, null),
    ABANDON(ApiState.REJECTED, ApiState.ARCHIVED, null),
    DEPRECATE(ApiState.PUBLISHED, ApiState.DEPRECATED, null),
    REACTIVATE(ApiState.DEPRECATED, ApiState.PUBLISHED, null),
    ARCHIVE(ApiState.DEPRECATED, ApiState.ARCHIVED, null);

    private final ApiState sourceState;
    private final ApiState targetState;
    private final ApiReviewStateChangeType reviewStateEvent;

    ApiStateChangeType(ApiState sourceState, ApiState targetState, ApiReviewStateChangeType reviewStateEvent) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.reviewStateEvent = reviewStateEvent;
    }

    public ApiState getSourceState(){
        return sourceState;
    }

    public ApiState getTargetState(){
        return targetState;
    }

    public ApiReviewStateChangeType getReviewStateEvent(){
        return reviewStateEvent;
    }
}
