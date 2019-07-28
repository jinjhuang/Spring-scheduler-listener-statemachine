package com.tw.techops.dp.api.eda.service;

import com.tw.techops.dp.api.eda.event.ApiEventType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.repo.ApiRepo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiService {

    @Resource
    private ApiStateMachineHandler apiStateMachineHandler;

    @Resource
    private ApiRepo apiRepo;

    public void create(Api api) {
        api = apiStateMachineHandler.handle(api, ApiEventType.CREATE);
        apiRepo.create(api);
    }

    public void update(Api api, ApiEventType eventType) {
        api = apiStateMachineHandler.handle(api, eventType);
        apiRepo.update(api);
    }

    public Api getByName(String name) {
        return apiRepo.get(name);
    }
}
