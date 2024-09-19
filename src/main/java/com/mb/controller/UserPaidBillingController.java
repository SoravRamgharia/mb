package com.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mb.entities.PaymentResponse;
import com.mb.entities.User;
import com.mb.helpers.Message;
import com.mb.helpers.MessageType;
import com.mb.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserPaidBillingController {
	@Autowired
	private EmailService emailService;

	public void processSendUserPaidBilling(User user, PaymentResponse paymentResponse) {

		String subject = "Send Paid Ekam_Marriage_Bureau Billing Receipt";

// Format the message
//		String MSG = contactName + contactEmail + contactNumber + contactFormFilledBy + contactMessage;
		String formattedMessage = paymentResponse.getUserId() + "\n " + paymentResponse.getRazorpayOrderId() + "\n "
				+ paymentResponse.getRazorpayPaymentId() + "\n " + paymentResponse.getRazorpaySignature() + "\n "
				+ paymentResponse.getValidityPeriod() + "\n " + paymentResponse.getValidityType();
		System.out.println("================>formattedMessage: \n" + formattedMessage);
		String message = "" + 
					"<div style='border:5px dotted #e47a2e;'>" + 
					"<h1 style='color: blue !important; text-align: center; text-decoration: underline dotted;' >" + "<span style='color: #E47A2E !important;'>Ekam</span> Marriage Bureau " + "</h1>"  +
					"<h3 style='color: #40ff00 !important; text-align: center;' >" + "( Thanks for choosing our Marriage Bureau )" + "</h3>" +"<hr>" +
					"<div style='color: black !important; padding:10px;'>"+
						"<h2>" + "UserId: " + "<span style='font-size: 0.80em !important;'>" + paymentResponse.getUserId() + "<span>" + "</h2> " + 
						"<h2>" + "RazorpayOrderId: " + "<span style='font-size: 0.80em !important;'>" + paymentResponse.getRazorpayOrderId() + "<span>" +"</h2> " +  
						"<h2>" + "RazorpayPaymentId: " + "<span style='font-size: 0.80em !important;'>" + paymentResponse.getRazorpayPaymentId() + "<span>" + "</h2> " + 
						"<h2>" + "You Paid at: " + "<span style='font-size: 0.80em !important;'>" + paymentResponse.getCreatedAt() + "<span>" + "</h2> " +   
						"<h2>" + "ValidityPeriod Duration: " + "<span style='font-size: 0.80em !important;'>" + paymentResponse.getValidityPeriod() + " " + paymentResponse.getValidityType() + "<span>" + "</h2> " +   
					"</div>"+
		"</div>";	
		String to = "soravs395@gmail.com";

		boolean flag = this.emailService.sendEmail(subject, message, to);

		if (flag) {
			System.out.println("Not Sended :)");
		} else {
			System.out.println("Not Send :(");
		}
	}
}
