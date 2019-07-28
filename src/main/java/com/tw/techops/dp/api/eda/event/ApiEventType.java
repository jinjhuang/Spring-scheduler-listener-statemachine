package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.state.ApiState;

public enum ApiEventType {
    CREATE(ApiState.INITIAL, ApiState.UNPUBLISHED),
    COMMIT(ApiState.UNPUBLISHED, ApiState.COMMITTED),
    REJECT(ApiState.COMMITTED, ApiState.REJECTED),
    PUBLISH(ApiState.COMMITTED, ApiState.PUBLISHED),
    REOPEN(ApiState.REJECTED, ApiState.UNPUBLISHED),
    ABANDON(ApiState.REJECTED, ApiState.ARCHIVED),
    DEPRECATE(ApiState.PUBLISHED, ApiState.DEPRECATED),
    REACTIVATE(ApiState.DEPRECATED, ApiState.PUBLISHED),
    ARCHIVED(ApiState.DEPRECATED, ApiState.ARCHIVED);

    private final ApiState sourceState;
    private final ApiState targetState;

    ApiEventType(ApiState sourceState, ApiState targetState) {
        this.sourceState = sourceState;
        this.targetState = targetState;
    }

    public ApiState getSourceState(){
        return sourceState;
    }

    public ApiState getTargetState(){
        return targetState;
    }
}
