package com.tw.techops.dp.api.eda.model;

import com.tw.techops.dp.api.eda.state.ApiState;
import lombok.Data;

@Data
public class Api {
    private Integer id;
    private String name;
    private ApiState state = ApiState.INITIAL;
}
