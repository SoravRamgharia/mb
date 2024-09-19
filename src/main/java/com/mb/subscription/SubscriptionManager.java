//package com.mb.subscription;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.mb.entities.PaymentResponse;
//import com.mb.entities.User;
//
//@Service
//public class SubscriptionManager {
//    private final SubscriptionChecker checker;
//
//    @Autowired
//    public SubscriptionManager(SubscriptionChecker checker) {
//        this.checker = checker;
//    }
//
//    public void startSubscription(PaymentResponse paymentResponse) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            User user = (User) authentication.getPrincipal(); // Get the authenticated user
//            if (user != null) {
//                Long userId = user.getUserId(); // Get the user ID
//                if (userId != null) {
//                    // Initialize the subscription
//                    user.setSubscriptionIsActive(true);
//                    checker.startChecking(paymentResponse);
//                } else {
//                    // Handle the case where userId is null
//                    log.error("User ID is null");
//                }
//            } else {
//                // Handle the case where user is null
//                log.error("User is null");
//            }
//        } else {
//            // Handle the case where authentication is null
//            log.error("Authentication is null");
//        }
//    }
//
//    public void stopSubscription() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            User user = (User) authentication.getPrincipal(); // Get the authenticated user
//            if (user != null) {
//                Long userId = user.getUserId(); // Get the user ID
//                if (userId != null) {
//                    // Stop the subscription
//                    user.setSubscriptionIsActive(false);
//                    checker.stopChecking();
//                } else {
//                    // Handle the case where userId is null
//                    log.error("User ID is null");
//                }
//            } else {
//                // Handle the case where user is null
//                log.error("User is null");
//            }
//        } else {
//            // Handle the case where authentication is null
//            log.error("Authentication is null");
//        }
//    }
//}