package com.tw.techops.dp.api.eda.model;

import com.tw.techops.dp.api.eda.state.ApiReviewState;

import java.sql.Timestamp;

public class ApiReview {
    private Integer id;
    private String comment;
    private Timestamp timestamp;
    private Api api;
    private ApiReviewState state = ApiReviewState.INTIAL;
}
