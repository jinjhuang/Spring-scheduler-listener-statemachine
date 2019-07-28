package com.tw.techops.dp.api.eda.model;

import com.tw.techops.dp.api.eda.state.ApiState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class Api {
    private Integer id;
    private String name;
    private ApiState state = ApiState.INITIAL;
    List<ApiReview> reviewList = new ArrayList<>();

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+this.id);
        sb.append(" name: "+this.name);
        sb.append(" state: "+ this.state);
        return sb.toString();
    }
}
