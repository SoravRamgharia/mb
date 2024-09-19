//package com.mb.subscription;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.mb.entities.PaymentResponse;
//import com.mb.entities.User;
//
//import jakarta.annotation.PostConstruct;
//
//@Service
//public class SubscriptionChecker {
//	private PaymentResponse paymentResponse;
//	private ScheduledExecutorService scheduler;
//	private ScheduledFuture<?> scheduledFuture;
//
//	public SubscriptionChecker(PaymentResponse paymentResponse) {
//		this.paymentResponse = paymentResponse;
//		this.scheduler = Executors.newSingleThreadScheduledExecutor();
//	}
//
//	@PostConstruct
//	public void init() {
//		startChecking();
//	}
//
//	public void startChecking() {
//		scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				checkSubscriptionValidity();
//				System.out.println("---------------------------------> scheduledFuture-Run: " + TimeUnit.MINUTES);
//			}
//		}, 0, 1, TimeUnit.MINUTES); // check every 1 minute
//
//		System.out.println("=========================================> scheduledFuture: " + scheduledFuture);
//	}
//
//	public void stopChecking() {
//		scheduledFuture.cancel(true);
//	}
//
//	private void checkSubscriptionValidity() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null) {
//			User user = (User) authentication.getPrincipal(); // Get the authenticated user
//			Long userId = user.getUserId(); // Get the user ID
//
//			if (!paymentResponse.isSubscriptionValid()) {
//				user.setSubscriptionIsActive(false);
//				// you can also notify the user or perform other actions here
//			}
//		}
//	}
//}