package com.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mb.helpers.Message;
import com.mb.helpers.MessageType;
import com.mb.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactUsController {
	@Autowired
	private EmailService emailService;

	@PostMapping("/send-contactusquery")
	public String processSendContactUsQuery(
			@RequestParam(value = "contactName", defaultValue = "Not Mentioned") String contactName,
			@RequestParam(value = "contactEmail", defaultValue = "Not Mentioned") String contactEmail,
			@RequestParam(value = "contactNumber", defaultValue = "Not Mentioned") String contactNumber,
			@RequestParam(value = "contactFormFilledBy", defaultValue = "Not Mentioned") String contactFormFilledBy,
			@RequestParam(value = "contactMessage", defaultValue = "Not Mentioned") String contactMessage,
			HttpSession session) {

		// Write Code for Send OTP to Email...

		String subject = "Ekam Marriage Bureau";
		
		// Format the message
//		String MSG = contactName + contactEmail + contactNumber + contactFormFilledBy + contactMessage;
		String formattedMessage = String.format(
				"ContactName: %s\nContactEmail: %s\nContactNumber: %s\nContactFormFilledBy: %s\nContactMessage: %s",
				contactName, contactEmail, contactNumber, contactFormFilledBy, contactMessage);
		System.out.println("================>formattedMessage: \n" + formattedMessage);
		String message = "" + 
		"<div style='border:5px dotted #e47a2e;'>" + 
					"<h1 style='color: blue !important; text-align: center;' >" + "<span style='color: #E47A2E !important;'>Ekam</span> Marriage Bureau " + "</h1>" +"<hr>" +
					"<div style='color: black !important; padding:10px;'>"+
						"<h2>" + "Name: " + "<span style='font-size: 0.80em !important;'>" + contactName + "<span>" + "</h2> " + 
						"<h2>" + "Email: " + "<span style='font-size: 0.80em !important;'>" + contactEmail + "<span>" +"</h2> " +  
						"<h2>" + "Number: " + "<span style='font-size: 0.80em !important;'>" + contactNumber + "<span>" + "</h2> " + 
						"<h2>" + "FilledBy: " + "<span style='font-size: 0.80em !important;'>" + contactFormFilledBy + "<span>" + "</h2> " +   
						"<h2>" + "Message: " + "<span style='font-size: 0.80em !important;'>" + contactMessage + "<span>" + "</h2> " + 
					"</div>"+
		"</div>";		
		String to = "soravs395@gmail.com";

		boolean flag = this.emailService.sendEmail(subject, message, to);

		if (flag) {
			Message msg = Message.builder().content("Message Send Succesfully :)").type(MessageType.green).build();
			session.setAttribute("message", msg);
			return "index";

		} else {
			Message msg = Message.builder().content("Someting is Wrong !!").type(MessageType.red).build();
			session.setAttribute("message", msg);
			return "index";
		}

	}
}
