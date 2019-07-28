package com.tw.techops.dp.api.eda.model;

import com.tw.techops.dp.api.eda.state.ApiReviewState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
public class ApiReview {
    private String id;
    private String comment;
    private Timestamp timestamp;
    private Api api;
    private ApiReviewState state = ApiReviewState.INTIAL;

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("id: "+this.id);
        sb.append(" comment: "+this.comment);
        sb.append(" state: "+ this.state);
        return sb.toString();
    }
}
