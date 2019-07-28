package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiStateChangeEvent;
import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.model.Api;
import com.tw.techops.dp.api.eda.repo.ApiRepo;
import com.tw.techops.dp.api.eda.state.ApiState;
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
public class ApiStateChangeListener  extends LifecycleObjectSupport {

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

    @Autowired
    private ApiRepo apiRepo;

    @Autowired
    private ApiStateChangeInterceptor apiStateChangeInterceptor;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiStateChangeListener.class);
    @Autowired
    private StateMachine<ApiState, ApiStateChangeType> apiStateMachine;

    @Override
    protected void onInit() {
        apiStateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.addStateMachineInterceptor(apiStateChangeInterceptor));
    }

    @EventListener
    public void handle(ApiStateChangeEvent apiStateChangeEvent) {
        Api api = apiStateChangeEvent.getSourceApi();
        ApiStateChangeType apiStateChangeType =  apiStateChangeEvent.getApiStateChange();
        ApiState newState =  this.handleEventWithState(MessageBuilder.withPayload(apiStateChangeType).setHeader("api", api).build(), api.getState());
        LOGGER.info(sdf.format(new Date())+": API: "+api.getName()+" is changed from: "+api.getState()+" to: "+newState);
        api.setState(newState);
        apiRepo.update(api);
    }

    private ApiState handleEventWithState(Message<ApiStateChangeType> event, ApiState state) {
        apiStateMachine.stop();
        List<StateMachineAccess<ApiState, ApiStateChangeType>> withAllRegions = apiStateMachine.getStateMachineAccessor()
                .withAllRegions();
        for (StateMachineAccess<ApiState, ApiStateChangeType> a : withAllRegions) {
            a.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
        }
        apiStateMachine.start();
        apiStateMachine.sendEvent(event);
        return apiStateMachine.getState().getId();
    }
}
