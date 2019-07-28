package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeEvent;
import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.model.ApiReview;
import com.tw.techops.dp.api.eda.repo.ApiReviewRepo;
import com.tw.techops.dp.api.eda.state.ApiReviewState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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
public class ApiReviewStateChangeListener extends LifecycleObjectSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiReviewStateChangeListener.class);
    @Autowired
    ApiReviewRepo apiReviewRepo;
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
    @Autowired
    private ApiReviewStateChangeInterceptor apiReviewStateChangeInterceptor;
    @Autowired
    private StateMachine<ApiReviewState, ApiReviewStateChangeType> apiReviewStateMachine;

    @Override
    protected void onInit() {
        apiReviewStateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.addStateMachineInterceptor(apiReviewStateChangeInterceptor));
    }

    @EventListener
    public void handle(ApiReviewStateChangeEvent apiReviewStateChangeEvent) {
        ApiReview apiReview = apiReviewStateChangeEvent.getSourceApiReview();
        ApiReviewStateChangeType apiReviewStateChangeType = apiReviewStateChangeEvent.getApiReviewStateChangeType();
        ApiReviewState newState = this.handleEventWithState(MessageBuilder.withPayload(apiReviewStateChangeType).setHeader("apiReview", apiReview).build(), apiReview.getState());
        LOGGER.info(sdf.format(new Date()) + ": ApiReview: " + apiReview.getId() + " is changed from: " + apiReview.getState() + " to: " + newState);
        apiReview.setState(newState);
        apiReviewRepo.update(apiReview);
    }

    private ApiReviewState handleEventWithState(Message<ApiReviewStateChangeType> event, ApiReviewState state) {
        apiReviewStateMachine.stop();
        List<StateMachineAccess<ApiReviewState, ApiReviewStateChangeType>> withAllRegions = apiReviewStateMachine.getStateMachineAccessor()
                .withAllRegions();
        for (StateMachineAccess<ApiReviewState, ApiReviewStateChangeType> a : withAllRegions) {
            a.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
        }
        apiReviewStateMachine.start();
        apiReviewStateMachine.sendEvent(event);
        return apiReviewStateMachine.getState().getId();
    }
}

