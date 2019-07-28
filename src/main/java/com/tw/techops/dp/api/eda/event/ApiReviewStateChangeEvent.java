package com.tw.techops.dp.api.eda.event;

import com.tw.techops.dp.api.eda.model.ApiReview;
import lombok.Data;

@Data
public class ApiReviewStateChangeEvent {
    private ApiReviewEventType evnetType;
    private ApiReview sourceApiReview;
}
