//package com.mb.config;


//package com.mb.config;
//
//import com.mb.services.SubscriptionValidatorJob;
//import com.mb.services.UserService;
//import org.quartz.SimpleScheduleBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
//import java.util.HashMap;
//
//import org.quartz.Job;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//
//@Configuration
//public class QuartzSchedulerConfig {
//
//    @Autowired
//    private UserService userService;
//
//    @Bean
//    @Scope("singleton")
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob(SubscriptionValidatorJob.class).withIdentity("subscriptionValidatorJob").storeDurably()
//                .build();
//    }
//
//    @Bean
//    @Scope("singleton")
//    public Trigger trigger() {
//        return TriggerBuilder.newTrigger().forJob(jobDetail()).withIdentity("subscriptionValidatorTrigger")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(3000) // Check every 3 seconds
//                        .repeatForever())
//                .build();
//    }
//
//    @Bean
//    @Scope("singleton")
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setJobDetails(jobDetail());
//        schedulerFactoryBean.setTriggers(trigger());
//        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
//        return schedulerFactoryBean;
//    }
//}
//
//
////package com.mb.config;
////
////import com.mb.services.SubscriptionValidatorJob;
////import org.quartz.SimpleScheduleBuilder;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.quartz.Job;
////import org.quartz.JobBuilder;
////import org.quartz.JobDetail;
////import org.quartz.JobExecutionContext;
////import org.quartz.JobExecutionException;
////import org.quartz.Trigger;
////import org.quartz.TriggerBuilder;
////import org.springframework.scheduling.quartz.SchedulerFactoryBean;
////
////@Configuration
////public class QuartzSchedulerConfig {
////
////	@Bean
////	public JobDetail jobDetail() {
////		return JobBuilder.newJob(SubscriptionValidatorJob.class).withIdentity("subscriptionValidatorJob").storeDurably()
////				.build();
////	}
////
////	@Bean
////	public Trigger trigger() {
////		return TriggerBuilder.newTrigger().forJob(jobDetail()).withIdentity("subscriptionValidatorTrigger")
////				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(30000) // Check every 30
////																										// seconds
////						.repeatForever())
////				.build();
////	}
////
////	@Bean
////	public SchedulerFactoryBean schedulerFactoryBean() {
////		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
////		schedulerFactoryBean.setJobDetails(jobDetail());
////		schedulerFactoryBean.setTriggers(trigger());
////		return schedulerFactoryBean;
////	}
////}