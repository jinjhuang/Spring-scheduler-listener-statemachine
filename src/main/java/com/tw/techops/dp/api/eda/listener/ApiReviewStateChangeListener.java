package com.tw.techops.dp.api.eda.listener;

import com.tw.techops.dp.api.eda.event.ApiReviewStateChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Profile("async")
public class ApiReviewStateChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiReviewStateChangeListener.class);

    @TransactionalEventListener
    public void processApiReviewEvent(ApiReviewStateChangeEvent event) {
        LOGGER.info("Event received: " + event.getEvnetType() + " from the source API:  " + event.getSourceApiReview());
    }
}
