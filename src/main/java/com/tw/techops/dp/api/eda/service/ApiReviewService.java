package com.tw.techops.dp.api.eda.service;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeEvent;
import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.repo.ApiReviewRepo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiReviewService {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    ApiReviewRepo apiReviewRepo;

    public void create(ApiReview apiReview) {
        ApiReviewStateChangeEvent apiReviewStateChangeEvent = new ApiReviewStateChangeEvent(apiReview, ApiReviewStateChangeType.CREATE);

        apiReviewRepo.create(apiReview);

        applicationEventPublisher.publishEvent(apiReviewStateChangeEvent);
    }

    public void update(ApiReview apiReview, ApiReviewStateChangeType eventType) {
        ApiReviewStateChangeEvent apiReviewStateChangeEvent = new ApiReviewStateChangeEvent(apiReview,eventType);

        apiReviewRepo.update(apiReview);

        applicationEventPublisher.publishEvent(apiReviewStateChangeEvent);
    }

    public ApiReview getById(String id) {
        return apiReviewRepo.get(id);
    }
}
