package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeEvent;
import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.state.ApiState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class ApiStateChangeInterceptor extends StateMachineInterceptorAdapter<ApiState, ApiStateChangeType> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void postStateChange(State<ApiState, ApiStateChangeType> state, Message<ApiStateChangeType> message,
                                Transition<ApiState, ApiStateChangeType> transition, StateMachine<ApiState, ApiStateChangeType> stateMachine) {
        Api api = message.getHeaders().get("api", Api.class);
        ApiStateChangeType apiEvent = message.getPayload();
        ApiReviewStateChangeType reviewEvent = apiEvent.getReviewStateEvent();
        if (null != reviewEvent) {
            for (ApiReview apiReview : api.getReviewList()) {
                ApiReviewStateChangeEvent apiReviewStateChangeEvent = new ApiReviewStateChangeEvent(apiReview, reviewEvent);
                applicationEventPublisher.publishEvent(apiReviewStateChangeEvent);
            }
        }
    }
}
