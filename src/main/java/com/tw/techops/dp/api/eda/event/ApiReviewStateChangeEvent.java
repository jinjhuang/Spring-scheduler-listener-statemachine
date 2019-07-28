package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.model.ApiReview;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ApiReviewStateChangeEvent {
    private ApiReviewStateChangeType apiReviewStateChangeType;
    private ApiReview sourceApiReview;

    public ApiReviewStateChangeEvent(ApiReview sourceApiReview,ApiReviewStateChangeType apiReviewStateChangeType){
        this.sourceApiReview = sourceApiReview;
        this.apiReviewStateChangeType = apiReviewStateChangeType;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("ApiReview: "+this.sourceApiReview.toString());
        sb.append(" ApiReviewStateChangeType: "+this.apiReviewStateChangeType);
        return sb.toString();
    }
}
