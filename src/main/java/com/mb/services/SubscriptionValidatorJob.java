//package com.mb.services;
//
//import org.hibernate.SessionFactory;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerContext;
//import org.quartz.SchedulerException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mb.entities.PaymentResponse;
//import com.mb.entities.User;
//import java.util.List;
//import org.springframework.context.ApplicationContext;
//import org.hibernate.Session;
//import org.hibernate.Hibernate;
//import org.hibernate.LockMode;
//
//@Service
//public class SubscriptionValidatorJob implements Job {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        try {
//            SchedulerContext schedulerContext = context.getScheduler().getContext();
//            ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
//            UserService userService = applicationContext.getBean(UserService.class);
//
//            Session session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//
//            try {
//                List<User> users = userService.getAllUsers();
//
//                for (User user : users) {
//                    session.lock(user, LockMode.NONE); // Ensure the user entity is attached to the session
//                    PaymentResponse paymentResponse = user.getPaymentResponse();
//                    if (paymentResponse != null) {
//                        Hibernate.initialize(paymentResponse); // Initialize the lazily loaded PaymentResponse entity
//                        if (paymentResponse.isSubscriptionValid()) {
//                            user.setSubscriptionIsActive(true);
//                        } else {
//                            user.setSubscriptionIsActive(false);
//                        }
//                        userService.saveUser(user);
//                    }
//                }
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                session.getTransaction().rollback();
//                e.printStackTrace();
//            } finally {
//                session.close();
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
////package com.mb.services;
////
////import java.util.List;
////
////import org.quartz.Job;
////import org.quartz.JobExecutionContext;
////import org.quartz.JobExecutionException;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import com.mb.entities.PaymentResponse;
////import com.mb.entities.User;
////
////@Service
////public class SubscriptionValidatorJob implements Job {
////    
////    @Autowired
////    private UserService userService;
////    
////    @Override
////    public void execute(JobExecutionContext context) throws JobExecutionException {
////        List<User> users = userService.getAllUsers();
////        
////        for (User user : users) {
////            PaymentResponse paymentResponse = user.getPaymentResponse();
////            if (paymentResponse != null) {
////                if (paymentResponse.isSubscriptionValid()) {
////                    user.setSubscriptionIsActive(true);
////                } else {
////                    user.setSubscriptionIsActive(false);
////                }
////                userService.saveUser(user);
////            }
////        }
////    }
////}