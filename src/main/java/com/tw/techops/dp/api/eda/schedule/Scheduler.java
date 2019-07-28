package com.tw.techops.dp.api.eda.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Configuration
@EnableScheduling
public class Scheduler {

    @PostConstruct
    public void onStartup() {
        scheduleTaskDuringReboot();
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }

    public void scheduleTaskDuringReboot() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks during reboot --------------------------- " + now);
    }
}
