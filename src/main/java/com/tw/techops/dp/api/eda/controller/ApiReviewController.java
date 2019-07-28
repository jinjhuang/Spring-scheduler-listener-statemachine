package com.tw.techops.dp.api.eda.controller;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.service.ApiReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class ApiReviewController {

    @Autowired
    private ApiReviewService apiReviewService;

    public void createApiReview(ApiReview apiReview){
        apiReviewService.create(apiReview);
    }

    public void updateApiReview(ApiReview apiReview, ApiReviewStateChangeType apiReviewStateChangeType){
        apiReviewService.update(apiReview,apiReviewStateChangeType);
    }

    public ApiReview getApiReviewById(String id){
        return apiReviewService.getById(id);
    }
}
