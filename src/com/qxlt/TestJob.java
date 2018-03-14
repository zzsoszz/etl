package com.qxlt;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        System.out.println("¡ï¡ï¡ï¡ï¡ï¡ï¡ï¡ï¡ï¡ï¡ï");
    }

}