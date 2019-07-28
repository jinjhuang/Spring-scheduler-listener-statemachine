package com.tw.techops.dp.api.eda.service;

import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeEvent;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.repo.ApiRepo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiService {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private ApiRepo apiRepo;

    public void create(Api api) {
        ApiStateChangeEvent apiStateChangeEvent = new ApiStateChangeEvent(api,ApiStateChangeType.CREATE);

        apiRepo.create(api);

        applicationEventPublisher.publishEvent(apiStateChangeEvent);
    }

    public void update(Api api, ApiStateChangeType eventType) {
        ApiStateChangeEvent apiStateChangeEvent = new ApiStateChangeEvent(api,eventType);

        apiRepo.update(api);

        applicationEventPublisher.publishEvent(apiStateChangeEvent);
    }

    public Api getByName(String name) {
        return apiRepo.get(name);
    }
}
