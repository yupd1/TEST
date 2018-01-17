package com.wondertek.mobilevideo.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class JobUtil {
	/**
	 * 创建JobDetail
	 * @param name
	 * @param cls
	 * @param params
	 * @return
	 */
	public JobDetail createJobDetail(String name,Class<?> cls,Map<String,String> params){
		JobDetail jobDetail = new JobDetail(name + "Job","DEFAULT",cls);
		if(params != null){
			for(String key : params.keySet()){
				jobDetail.getJobDataMap().put(key, params.get(key));
			}
		}
		return jobDetail;
	}
	/**
	 * 创建Job
	 * @param name
	 * @param cls
	 * @param params
	 * @param delayMilliSecond
	 * @param repeatInterval
	 * @param repeatCount
	 */
	public void createJob(String schedulerName,String name,Class<?> cls,Map<String,String> params,int delayMilliSecond,long repeatInterval,int repeatCount){
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler(schedulerName);
			
			SimpleTrigger simpleTrigger = new SimpleTrigger(name + "Trigger",Scheduler.DEFAULT_GROUP);
			Calendar s = Calendar.getInstance();
			s.setTime(new Date());
			s.add(Calendar.MILLISECOND, delayMilliSecond);
			simpleTrigger.setRepeatInterval(repeatInterval);
			simpleTrigger.setRepeatCount(repeatCount);
			simpleTrigger.setStartTime(s.getTime());
			
			scheduler.scheduleJob(createJobDetail(name,cls,params), simpleTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
}
