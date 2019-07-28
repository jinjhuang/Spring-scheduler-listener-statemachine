package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiReviewEventType;
import com.tw.techops.dp.api.eda.state.ApiReviewState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "apiReviewStateMachine")
public class ApiReviewStateMachineConfiguration extends StateMachineConfigurerAdapter<ApiReviewState, ApiReviewEventType> {
    @Override
    public void configure(StateMachineStateConfigurer<ApiReviewState, ApiReviewEventType> states)
            throws Exception {

        states.withStates()
                .initial(ApiReviewState.NEW)
                .end(ApiReviewState.APPROVED)
                .end(ApiReviewState.REJECTED)
                .states(EnumSet.allOf(ApiReviewState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApiReviewState, ApiReviewEventType> transitions) throws Exception {
        for (ApiReviewEventType eventType : ApiReviewEventType.values()) {
            transitions = transitions.withExternal()
                    .source(eventType.getSourceState())
                    .target(eventType.getTargetState())
                    .event(eventType).and();
        }
    }
}
