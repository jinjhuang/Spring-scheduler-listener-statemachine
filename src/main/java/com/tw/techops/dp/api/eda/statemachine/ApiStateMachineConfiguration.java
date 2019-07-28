package com.tw.techops.dp.api.eda.statemachine;

import com.tw.techops.dp.api.eda.event.ApiStateChangeType;
import com.tw.techops.dp.api.eda.state.ApiState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "apiStateMachine")
public class ApiStateMachineConfiguration extends StateMachineConfigurerAdapter<ApiState, ApiStateChangeType> {

    @Override
    public void configure(StateMachineStateConfigurer<ApiState, ApiStateChangeType> states)
            throws Exception {

        states.withStates()
                .initial(ApiState.INITIAL)
                .end(ApiState.ARCHIVED)
                .states(EnumSet.allOf(ApiState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApiState, ApiStateChangeType> transitions) throws Exception {
        for (ApiStateChangeType eventType : ApiStateChangeType.values()) {
            transitions = transitions.withExternal()
                    .source(eventType.getSourceState())
                    .target(eventType.getTargetState())
                    .event(eventType).and();
        }
    }
}
