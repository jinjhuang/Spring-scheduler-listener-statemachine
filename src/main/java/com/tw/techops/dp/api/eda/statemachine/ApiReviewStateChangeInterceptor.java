package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeEvent;
import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.state.ApiReviewState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class ApiReviewStateChangeInterceptor extends StateMachineInterceptorAdapter<ApiReviewState, ApiReviewStateChangeType> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiReviewStateChangeInterceptor.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void postStateChange(State<ApiReviewState, ApiReviewStateChangeType> state, Message<ApiReviewStateChangeType> message,
                                Transition<ApiReviewState, ApiReviewStateChangeType> transition, StateMachine<ApiReviewState, ApiReviewStateChangeType> stateMachine) {
        ApiReview apiReview = message.getHeaders().get("apiReview", ApiReview.class);
        ApiReviewStateChangeType apiReviewStateChangeType = message.getPayload();
        ApiStateChangeType apiStateChangeType = apiReviewStateChangeType.getApiStateEvent();
        if (null != apiStateChangeType) {
            ApiStateChangeEvent apiStateChangeEvent = new ApiStateChangeEvent(apiReview.getApi(), apiStateChangeType);
            LOGGER.info(">>>>>>>>>>>>>>>>" + apiStateChangeEvent.toString());
            applicationEventPublisher.publishEvent(apiStateChangeEvent);
        }
    }
}
