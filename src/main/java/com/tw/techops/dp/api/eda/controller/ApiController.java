package com.tw.techops.dp.api.eda.controller;

import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class ApiController {

    @Autowired
    private ApiService apiService;

    public void createApi(Api api){
        apiService.create(api);
    }

    public void updateApi(Api api, ApiStateChangeType eventType){
        apiService.update(api,eventType);
    }

    public Api getApiByName(String name){
        return apiService.getByName(name);
    }
}
