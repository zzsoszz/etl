package com.qxlt;

public class QuartzTest {

    /** *//**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TestJob job = new TestJob();
        String job_name ="11";
        try {
            System.out.println("��ϵͳ������");
            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");
            
            Thread.sleep(10000);
            System.out.println("���޸�ʱ�䡿");
            QuartzManager.modifyJobTime(job_name,"0/10 * * * * ?");
            Thread.sleep(20000);
            System.out.println("���Ƴ���ʱ��");
            QuartzManager.removeJob(job_name);
            Thread.sleep(10000);
            
            System.out.println("\n����Ӷ�ʱ����");
            QuartzManager.addJob(job_name,job,"0/5 * * * * ?");
            
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}