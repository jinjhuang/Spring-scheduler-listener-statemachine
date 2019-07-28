package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeType;
import com.tw.techops.dp.api.eda.state.ApiReviewState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "apiReviewStateMachine")
public class ApiReviewStateMachineConfiguration extends StateMachineConfigurerAdapter<ApiReviewState, ApiReviewStateChangeType> {
    @Override
    public void configure(StateMachineStateConfigurer<ApiReviewState, ApiReviewStateChangeType> states)
            throws Exception {

        states.withStates()
                .initial(ApiReviewState.NEW)
                .end(ApiReviewState.APPROVED)
                .end(ApiReviewState.REJECTED)
                .states(EnumSet.allOf(ApiReviewState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApiReviewState, ApiReviewStateChangeType> transitions) throws Exception {
        for (ApiReviewStateChangeType eventType : ApiReviewStateChangeType.values()) {
            transitions = transitions.withExternal()
                    .source(eventType.getSourceState())
                    .target(eventType.getTargetState())
                    .event(eventType).and();
        }
    }
}
