package com.tw.techops.dp.api.eda.service;

import com.tw.techops.dp.api.eda.event.ApiEventType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.state.ApiState;
import com.tw.techops.dp.api.eda.statemachine.ApiStateChangeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ApiStateMachineHandler extends LifecycleObjectSupport {

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiStateMachineHandler.class);
    @Autowired
    private StateMachine<ApiState, ApiEventType> apiStateMachine;

    @Override
    protected void onInit() {
        apiStateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.addStateMachineInterceptor(new ApiStateChangeInterceptor()));
    }

    public Api handle(Api api, ApiEventType eventType) {
        ApiState newState =  this.handleEventWithState(MessageBuilder.withPayload(eventType).setHeader("apiName", api.getName()).build(), api.getState());
        LOGGER.info(sdf.format(new Date())+": API: "+api.getName()+" is changed from: "+api.getState()+" to: "+newState);
        api.setState(newState);
        return api;
    }

    private ApiState handleEventWithState(Message<ApiEventType> event, ApiState state) {
        apiStateMachine.stop();
        List<StateMachineAccess<ApiState, ApiEventType>> withAllRegions = apiStateMachine.getStateMachineAccessor()
                .withAllRegions();
        for (StateMachineAccess<ApiState, ApiEventType> a : withAllRegions) {
            a.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
        }
        apiStateMachine.start();
        apiStateMachine.sendEvent(event);
        return apiStateMachine.getState().getId();
    }
}
