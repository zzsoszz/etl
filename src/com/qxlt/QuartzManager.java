package com.qxlt;

import java.text.ParseException;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/** *//**
 * @Title:Quartz������
 * 
 * @Description:
 * 
 * @Copyright: 
 * @author zz  2008-10-8 14:19:01
 * @version 1.00.000
 *
 */
public class QuartzManager {
   private static SchedulerFactory sf = new StdSchedulerFactory();
   private static String JOB_GROUP_NAME = "group1";
   private static String TRIGGER_GROUP_NAME = "trigger1";
  
   
   /** *//**
    *  ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
    * @param jobName ������
    * @param job     ����
    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void addJob(String jobName,Job job,String time) 
                               throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//�������������飬����ִ����
       //������
       CronTrigger  trigger = 
            new CronTrigger(jobName, TRIGGER_GROUP_NAME);//��������,��������
       trigger.setCronExpression(time);//������ʱ���趨
       sched.scheduleJob(jobDetail,trigger);
       //����
       if(!sched.isShutdown())
          sched.start();
   }
   
   /** *//**
    * ���һ����ʱ����
    * @param jobName ������
    * @param jobGroupName ��������
    * @param triggerName  ��������
    * @param triggerGroupName ����������
    * @param job     ����
    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void addJob(String jobName,String jobGroupName,
                             String triggerName,String triggerGroupName,
                             Job job,String time) 
                               throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//�������������飬����ִ����
       //������
       CronTrigger  trigger = 
            new CronTrigger(triggerName, triggerGroupName);//��������,��������
       trigger.setCronExpression(time);//������ʱ���趨
       sched.scheduleJob(jobDetail,trigger);
       if(!sched.isShutdown())
          sched.start();
   }
   
   /** *//**
    * �޸�һ������Ĵ���ʱ��(ʹ��Ĭ�ϵ�������������������������������)
    * @param jobName
    * @param time
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void modifyJobTime(String jobName,String time) 
                                  throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
       if(trigger != null){
           CronTrigger  ct = (CronTrigger)trigger;
           ct.setCronExpression(time);
           sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);
       }
   }
   
   /** *//**
    * �޸�һ������Ĵ���ʱ��
    * @param triggerName
    * @param triggerGroupName
    * @param time
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void modifyJobTime(String triggerName,String triggerGroupName,
                                    String time) 
                                  throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       Trigger trigger =  sched.getTrigger(triggerName,triggerGroupName);
       if(trigger != null){
           CronTrigger  ct = (CronTrigger)trigger;
           //�޸�ʱ��
           ct.setCronExpression(time);
           //����������
           sched.resumeTrigger(triggerName,triggerGroupName);
       }
   }
   
   /** *//**
    * �Ƴ�һ������(ʹ��Ĭ�ϵ�������������������������������)
    * @param jobName
    * @throws SchedulerException
    */
   public static void removeJob(String jobName) 
                               throws SchedulerException{
       Scheduler sched = sf.getScheduler();
       sched.pauseTrigger(jobName,TRIGGER_GROUP_NAME);//ֹͣ������
       sched.unscheduleJob(jobName,TRIGGER_GROUP_NAME);//�Ƴ�������
       sched.deleteJob(jobName,JOB_GROUP_NAME);//ɾ������
   }
   
   /** *//**
    * �Ƴ�һ������
    * @param jobName
    * @param jobGroupName
    * @param triggerName
    * @param triggerGroupName
    * @throws SchedulerException
    */
   public static void removeJob(String jobName,String jobGroupName,
                                String triggerName,String triggerGroupName) 
                               throws SchedulerException{
       Scheduler sched = sf.getScheduler();
       sched.pauseTrigger(triggerName,triggerGroupName);//ֹͣ������
       sched.unscheduleJob(triggerName,triggerGroupName);//�Ƴ�������
       sched.deleteJob(jobName,jobGroupName);//ɾ������
   }
}
