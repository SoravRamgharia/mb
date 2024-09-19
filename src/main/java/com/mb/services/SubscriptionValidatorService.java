package com.mb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mb.entities.PaymentResponse;
import com.mb.entities.User;

@Service
public class SubscriptionValidatorService {
    
    @Autowired
    private UserService userService;
    
//    @Scheduled(fixedRate = 30000) // Check every 30 seconds
//    public void validateSubscriptions() {
//        List<User> users = userService.getAllUsers();
//        
//        for (User user : users) {
//            PaymentResponse paymentResponse = user.getPaymentResponse();
//            if (paymentResponse != null) {
//                if (paymentResponse.isSubscriptionValid()) {
//                    user.setSubscriptionIsActive(true);
//                } else {
//                    user.setSubscriptionIsActive(false);
//                }
//                userService.saveUser(user);
//            }
//        }
//    }
}
