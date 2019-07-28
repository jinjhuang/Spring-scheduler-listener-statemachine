package com.tw.techops.dp.api.eda.repo;

import com.tw.techops.dp.api.eda.model.Api;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiRepo {

    public static Map<String, Api> API_STORE = new HashMap<>();

    public void create(Api api) {
        API_STORE.put(api.getName(),api);
    }

    public void update(Api api) {
        API_STORE.put(api.getName(),api);
    }

    public Api get(String name) {
        return API_STORE.get(name);
    }
}
