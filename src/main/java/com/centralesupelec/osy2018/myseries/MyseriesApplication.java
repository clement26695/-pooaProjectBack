package com.centralesupelec.osy2018.myseries;

import com.centralesupelec.osy2018.myseries.utils.cronjobs.CronThread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyseriesApplication {

	public static void main(String[] args) {
        SpringApplication.run(MyseriesApplication.class, args);

        CronThread t = new CronThread();
        t.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            t.arreter();
        }
	}
}
