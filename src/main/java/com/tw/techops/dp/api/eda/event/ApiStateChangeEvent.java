package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.model.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ApiStateChangeEvent {
    private ApiStateChangeType apiStateChange;
    private Api sourceApi;

    public ApiStateChangeEvent(Api sourceApi,ApiStateChangeType apiStateChange){
        this.sourceApi = sourceApi;
        this.apiStateChange = apiStateChange;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("API: "+this.sourceApi.toString());
        sb.append(" ApiStateChangeType: "+this.apiStateChange);
        return sb.toString();
    }
}
