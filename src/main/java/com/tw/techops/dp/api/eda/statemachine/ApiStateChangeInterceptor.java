package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiEventType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeEvent;
import com.tw.techops.dp.api.eda.state.ApiState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiStateChangeInterceptor extends StateMachineInterceptorAdapter<ApiState, ApiEventType> {

    @Resource
    private  ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void postStateChange(State<ApiState, ApiEventType> state, Message<ApiEventType> message,
                                Transition<ApiState, ApiEventType> transition, StateMachine<ApiState, ApiEventType> stateMachine) {
        String apiName = message.getHeaders().get("apiName", String.class);
        ApiStateChangeEvent event = new ApiStateChangeEvent(message.getPayload(), apiName);
        applicationEventPublisher.publishEvent(event);
    }
}
