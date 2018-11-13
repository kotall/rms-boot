package com.kotall.rms.core.model.quartz;

import com.kotall.rms.core.enums.ScheduleStatus;
import com.kotall.rms.core.RmsException;
import com.kotall.rms.common.utils.JSONUtils;
import com.kotall.rms.common.utils.SpringContextUtils;
import com.kotall.rms.common.entity.sys.SysJobEntity;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * 定时任务工具类
 *
 * @author aracwong
 * @date 2017年8月20日 下午10:49:11
 */
public class ScheduleUtils {
    
	private static Scheduler scheduler = (Scheduler) SpringContextUtils.getBean("scheduler");
	
	private final static String JOB_NAME = "TASK_";
    
    /**
     * 获取触发器key
     * @param jobId
     * @return
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * 获取jobKey
     * @param jobId
     * @return
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RmsException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     * @param scheduleJob
     */
    public static void createScheduleJob(SysJobEntity scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(SysJobEntity.JOB_PARAM_KEY, JSONUtils.beanToJson(scheduleJob));
            
            scheduler.scheduleJob(jobDetail, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new RmsException("创建定时任务失败", e);
        }
    }
    
    /**
     * 更新定时任务
     * @param scheduleJob
     */
    public static void updateScheduleJob(SysJobEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduleJob.getJobId());
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //参数
            trigger.getJobDataMap().put(SysJobEntity.JOB_PARAM_KEY, JSONUtils.beanToJson(scheduleJob));
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //暂停任务
            if(scheduleJob.getStatus() == ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduleJob.getJobId());
            }
            
        } catch (SchedulerException e) {
            throw new RmsException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     * @param scheduleJob
     */
    public static void run(SysJobEntity scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(SysJobEntity.JOB_PARAM_KEY, JSONUtils.beanToJson(scheduleJob));
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            throw new RmsException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     * @param jobId
     */
    public static void pauseJob(Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RmsException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     * @param jobId
     */
    public static void resumeJob(Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RmsException("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     * @param jobId
     */
    public static void deleteScheduleJob(Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RmsException("删除定时任务失败", e);
        }
    }
}
