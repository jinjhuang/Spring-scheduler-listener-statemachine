package com.tw.techops.dp.api.eda.repo;

import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.model.ApiReview;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiReviewRepo {

    public static Map<String, ApiReview> API_REVIEW_STORE = new HashMap<>();

    public void create(ApiReview apiReview) {
        API_REVIEW_STORE.put(apiReview.getId(),apiReview);
    }

    public void update(ApiReview apiReview) {
        API_REVIEW_STORE.put(apiReview.getId(),apiReview);
    }

    public ApiReview get(String id) {
        return API_REVIEW_STORE.get(id);
    }
}
