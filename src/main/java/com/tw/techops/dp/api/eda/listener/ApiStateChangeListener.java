package com.tw.techops.dp.api.eda.listener;

import com.tw.techops.dp.api.eda.event.ApiEventType;
import com.tw.techops.dp.api.eda.event.ApiStateChangeEvent;
import com.tw.techops.dp.api.eda.state.ApiState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

@Component
@Profile("async")
public class ApiStateChangeListener  extends StateMachineListenerAdapter<ApiState, ApiEventType> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiStateChangeListener.class);

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @TransactionalEventListener
    public void processApiEvent(ApiStateChangeEvent event) {
        LOGGER.info("Event received: " + event.getEvnetType() + " from the source API:  " + event.getApiName());
        switch(event.getEvnetType()){
            case COMMIT:

                applicationEventPublisher.publishEvent();
        }
    }

    @Override
    public void stateChanged(State<ApiState, ApiEventType> from, State<ApiState, ApiEventType> to) {
        LOGGER.info(String.format("Transitioned from %s to %s%n", from == null ? "none" : from.getId(), to.getId()));

    }
}
