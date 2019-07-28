package com.tw.techops.dp.api.eda.event;

import lombok.Data;

@Data
public class ApiStateChangeEvent {
    private ApiEventType evnetType;
    private String apiName;

    public ApiStateChangeEvent(ApiEventType evnetType, String apiName){
        this.evnetType = evnetType;
        this.apiName = apiName;
    }
}
