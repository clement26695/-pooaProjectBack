package com.centralesupelec.osy2018.myseries.utils.cronjobs;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronThread extends Thread {
    private volatile boolean running = true;

    public void arreter() {
        this.running = false;
    }

    @Override
    public void run() {
        Scheduler scheduler1 = null;
        try {
            JobDetail job1 = JobBuilder.newJob(NotificationTask.class).withIdentity("job1", "group1").build();

            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

            scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);

        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (running) {

        }

        try {
            scheduler1.shutdown();
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
