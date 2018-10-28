package com.centralesupelec.osy2018.myseries.utils.cronjobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NotificationTask implements Job {
    public NotificationTask() {
        // Some stuffs
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Job1 --->>> Hello geeks! Time is " + new Date());
    }

}
