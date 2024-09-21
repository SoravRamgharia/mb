package com.mb.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mb.entities.PaymentResponse;
import com.mb.entities.User;
import com.mb.forms.UserForm;
import com.mb.forms.UserFormDetails;
import com.mb.helpers.Helper;
import com.mb.helpers.Message;
import com.mb.helpers.MessageType;
import com.mb.helpers.UserDefaultValues;
import com.mb.services.ImageService;
import com.mb.services.PaymentService;
import com.mb.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginUserCRUD {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(PageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserDefaultValues userDefaultValues;

	@Autowired
	private PaymentService paymentService;

	// Showing Particular User Details.....
	@RequestMapping("/user/{userId}")
	public String showUserDetail(@PathVariable("userId") Long userId, Model model) {

		System.out.println("userId of User: " + userId);

		Optional<User> userOptional = this.userService.getUserById(userId);
		User userData = userOptional.get();

//		String username = Helper.getEmailOfLoggedInUser(authentication);
//		User userData = userService.getUserByEmail(username);

		System.out.println("userData.getPaymentResponse()----------------->");
		System.out.println(userData);
		if (userData.getPaymentResponse() != null) {
			System.out.println(userData.getPaymentResponse());
			System.out.println(userData.getPaymentResponse().toString());
			System.out.println(userData.getPaymentResponse().getRazorpayOrderId());
			System.out.println(userData.getPaymentResponse().getRazorpayPaymentId());
			System.out.println(userData.getPaymentResponse().getRazorpaySignature());
		}

		if (paymentService.getUserById(userData.getUserId()).isEmpty()) {
			System.out.println("isEmpty(): " + paymentService.getUserById(userData.getUserId()).isEmpty());
			model.addAttribute("paymentResponse", "empty");
		}
		if (paymentService.getUserById(userData.getUserId()).isPresent()) {
			Optional<PaymentResponse> paymentResponse = paymentService.getUserById(userData.getUserId());
			System.out.println("isPresent(): " + paymentService.getUserById(userData.getUserId()).isPresent());
			PaymentResponse response = paymentResponse.get();
			System.out.println("response: " + response);
			model.addAttribute("paymentResponse", response);
		}

		System.out.println("\n\n\nThis is userId from user.... Process UserProfile  Handler");

		model.addAttribute("user", userData);
		model.addAttribute("userImages", userData.getImages());

		return "User/userprofile";
	}

	// Showing Particular User Details.....
	@RequestMapping("/user/logginprofile")
	public String showLoginUserDetail(Model model, Authentication authentication) {

		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		System.out.println("\n\n\nThis is LoginUser from logginprofile.... Process showLoginUserDetail  Handler");
		System.out.println("User ID: " + userData.getUserId() + ", Gender: " + userData.getGender() + ", Religion: "
				+ userData.getReligion() + ", Caste: " + userData.getCaste() + ", Age: " + userData.getAge()
				+ ", MinAge: " + userData.getMinAge() + ", MaxAge: " + userData.getMaxAge() + ", MinHeight: "
				+ userData.getMinHeight() + ", Height: " + userData.getHeight() + ", MaxHeight: "
				+ userData.getMaxHeight() + ", Picture: " + userData.getPicture() + ", MarriedStatus: "
				+ userData.getMarriedStatus() + ", Place: " + userData.getPlace() + ", Occupation: "
				+ userData.getOccupation());

		System.out.println("userData.getPaymentResponse()----------------->");
		System.out.println(userData);
		if (userData.getPaymentResponse() != null) {
			System.out.println(userData.getPaymentResponse());
			System.out.println(userData.getPaymentResponse().toString());
			System.out.println(userData.getPaymentResponse().getRazorpayOrderId());
			System.out.println(userData.getPaymentResponse().getRazorpayPaymentId());
			System.out.println(userData.getPaymentResponse().getRazorpaySignature());
		}

		if (paymentService.getUserById(userData.getUserId()).isEmpty()) {
			System.out.println("isEmpty(): " + paymentService.getUserById(userData.getUserId()).isEmpty());
			model.addAttribute("paymentResponse", "empty");
		}
		if (paymentService.getUserById(userData.getUserId()).isPresent()) {
			Optional<PaymentResponse> paymentResponse = paymentService.getUserById(userData.getUserId());
			System.out.println("isPresent(): " + paymentService.getUserById(userData.getUserId()).isPresent());
			PaymentResponse response = paymentResponse.get();
			System.out.println("response: " + response);
			model.addAttribute("paymentResponse", response);
		}
//		if (paymentResponse.isEmpty())
//			System.out.println("paymentResponse isEmpty(): " + paymentResponse);
//
//		if (paymentResponse.isPresent())
//			System.out.println("paymentResponse isPresent(): " + paymentResponse);
////		System.out.println("paymentResponse.get(): " + paymentResponse.get());

//		if (paymentResponse.isEmpty()) {
//			model.addAttribute("paymentResponse", "empty"); // or some default value
//		} else {
//			PaymentResponse response = paymentResponse.get();
//			System.out.println("response: " + response);
//			model.addAttribute("paymentResponse", response);
//		}

//		List<PaymentResponse> allPaymentResponses = paymentService.getAllPaymentResponses();
//		for (PaymentResponse pr : allPaymentResponses) {
//			System.out.println("pr: " + pr);
//		}

		model.addAttribute("user", userData);
		model.addAttribute("userImages", userData.getImages());

		return "User/userprofile";
	}

//  Open Update_Contact Handler----->
	@GetMapping("/view/userDetailsUpdateForm")
	public String updateUserFormView(Model model, Authentication authentication) {

		System.out.println("updateUserFormView Handler..........");
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		UserFormDetails userFormDetails = new UserFormDetails();

		userFormDetails.setYourName(userData.getName());
		userFormDetails.setGender(userData.getGender());
		userFormDetails.setReligion(userData.getReligion());
		userFormDetails.setCaste(userData.getCaste());
		userFormDetails.setSubcaste(userData.getSubcaste());

		userFormDetails.setDateOfBirth(userData.getDateOfBirth());
		userFormDetails.setAge(userData.getAge());
		userFormDetails.setBrithTime(userData.getBrithTime());
		userFormDetails.setHeight(userData.getHeight());
		userFormDetails.setMarriedStatus(userData.getMarriedStatus());
		userFormDetails.setPlace(userData.getPlace());
		userFormDetails.setNriPlace(userData.getNriPlace());
		userFormDetails.setQualification(userData.getQualification());
		userFormDetails.setQualificationField(userData.getQualificationField());
		userFormDetails.setOccupation(userData.getOccupation());
		userFormDetails.setYourJobTitle(userData.getYourJobTitle());
		userFormDetails.setYourJobSalary(userData.getYourJobSalary());

		userFormDetails.setFamilyStatus(userData.getFamilyStatus());
		userFormDetails.setTotalFamilyMembers(userData.getTotalFamilyMembers());
		userFormDetails.setTotalBrothers(userData.getTotalBrothers());
		userFormDetails.setTotalSisters(userData.getTotalSisters());

		userFormDetails.setFatherName(userData.getFatherName());
		userFormDetails.setFatherOccupation(userData.getFatherOccupation());
		userFormDetails.setFatherJobTitle(userData.getFatherJobTitle());
		userFormDetails.setFatherJobSalary(userData.getFatherJobSalary());

		userFormDetails.setMotherName(userData.getMotherName());
		userFormDetails.setMotherOccupation(userData.getMotherOccupation());
		userFormDetails.setMotherJobTitle(userData.getMotherJobTitle());
		userFormDetails.setMotherJobSalary(userData.getMotherJobSalary());

		userFormDetails.setAnyDemand(userData.getAnyDemand());
		userFormDetails.setAnyRemarks(userData.getAnyRemarks());
		userFormDetails.setAddress(userData.getAddress());

		userFormDetails.setPhoneNumber1(userData.getPhoneNumber1());
		userFormDetails.setPhoneNumber1(userData.getPhoneNumber1());
		userFormDetails.setPhoneNumber2(userData.getPhoneNumber2());

		userFormDetails.setFormFilledBy(userData.getFormFilledBy());

		model.addAttribute("userFormDetails", userFormDetails);
		model.addAttribute("userImages", userData.getImages());

		return "User/update_user_view";
	}

//  Processing for Update_Contact Handler----->
	@PostMapping("/update/userDetailsUpdateForm")
	public String processUpdateUserFormView(@Valid @ModelAttribute("userFormDetails") UserFormDetails userFormDetails,
			BindingResult bindingResult, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam("userImages") List<MultipartFile> userImages, Model model, Authentication authentication,
			HttpSession session) throws Exception {

		System.out.println("processUpdateUserFormView Handler..........");
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		// update the contact
		if (bindingResult.hasErrors()) {
			return "user/update_user_view";
		}

		userDefaultValues.setDefaultValues(userFormDetails);

		userData.setName(userFormDetails.getYourName());
		userData.setGender(userFormDetails.getGender());
		userData.setReligion(userFormDetails.getReligion());
		userData.setCaste(userFormDetails.getCaste());
		userData.setSubcaste(userFormDetails.getSubcaste());

//		String dateOfBirth = userFormDetails.getDateOfBirth();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
//		int age = Period.between(dob, LocalDate.now()).getYears();
		
		// Getting Age from DOB...
		String dateOfBirth = userFormDetails.getDateOfBirth(); // This is in "MM/dd/yyyy"
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dob = LocalDate.parse(dateOfBirth, inputFormatter); // Parse input date
		String formattedDateOfBirth = dob.format(outputFormatter); // Format to "dd/MM/yyyy"

		int age = Period.between(dob, LocalDate.now()).getYears(); // Calculate age


		userData.setDateOfBirth(dateOfBirth);
		userData.setAge(age);
		userData.setBrithTime(userFormDetails.getBrithTime());
		userData.setHeight(userFormDetails.getHeight());
		userData.setMarriedStatus(userFormDetails.getMarriedStatus());
		userData.setPlace(userFormDetails.getPlace());
		userData.setNriPlace(userFormDetails.getNriPlace());
		userData.setQualification(userFormDetails.getQualification());
		userData.setQualificationField(userFormDetails.getQualificationField());
		userData.setOccupation(userFormDetails.getOccupation());
		userData.setYourJobTitle(userFormDetails.getYourJobTitle());
		userData.setYourJobSalary(userFormDetails.getYourJobSalary());

		userData.setFamilyStatus(userFormDetails.getFamilyStatus());
		userData.setTotalFamilyMembers(userFormDetails.getTotalFamilyMembers());
		userData.setTotalBrothers(userFormDetails.getTotalBrothers());
		userData.setTotalSisters(userFormDetails.getTotalSisters());

		userData.setFatherName(userFormDetails.getFatherName());
		userData.setFatherOccupation(userFormDetails.getFatherOccupation());
		userData.setFatherJobTitle(userFormDetails.getFatherJobTitle());
		userData.setFatherJobSalary(userFormDetails.getFatherJobSalary());

		userData.setMotherName(userFormDetails.getMotherName());
		userData.setMotherOccupation(userFormDetails.getMotherOccupation());
		userData.setMotherJobTitle(userFormDetails.getMotherJobTitle());
		userData.setMotherJobSalary(userFormDetails.getMotherJobSalary());

		userData.setAnyDemand(userFormDetails.getAnyDemand());
		userData.setAnyRemarks(userFormDetails.getAnyRemarks());
		userData.setAddress(userFormDetails.getAddress());

		userData.setPhoneNumber1(userFormDetails.getPhoneNumber1());
		userData.setPhoneNumber1(userFormDetails.getPhoneNumber1());
		userData.setPhoneNumber2(userFormDetails.getPhoneNumber2());

		userData.setFormFilledBy(userFormDetails.getFormFilledBy());

		if (userImages != null && !userImages.isEmpty()) {
			List<String> imageUrls = imageService.uploadImages(userImages, UUID.randomUUID().toString());
			List<String> publicIds = new ArrayList<>();

			for (int i = 0; i < imageUrls.size(); i++) {
				publicIds.add(UUID.randomUUID().toString());
			}

			userData.setPicture(imageUrls.get(0)); // set the first image as the profile picture
//			userData.setCloudinaryImagePublicId(publicIds.get(0)); // set the first image as the cloudinary image public
			// id
			userData.setImages(imageUrls); // store the list of image URLs
//			userData.setCloudinaryImagePublicIds(publicIds); // store the list of public IDs

			System.out.println("URL-------------------");
			System.out.println(imageUrls);
			System.out.println("Public IDs-------------------");
			System.out.println(publicIds);
		}

		var updateUser = userService.updateUser(userData);
		logger.info("Updated User {}", updateUser);

		model.addAttribute("message", Message.builder().content("User Updated !!").type(MessageType.green).build());

		// Adding Message that Register Successfully :)
		Message message = Message.builder().content("Your Data is Updated Successful :)").type(MessageType.green)
				.build();
		session.setAttribute("message", message);

		return "redirect:/user/logginprofile";
	}

	// Delete User Client Handler----->
	@GetMapping("/do-deleteclient/{userId}")
	public String deleteUserByClient(@PathVariable("userId") Long userId, Model model, HttpSession session) {

		System.out.println("deleteUser Handler..........");
		Optional<User> userOptional = this.userService.getUserById(userId);
		User userData = userOptional.get();

		// delete the user
		userService.deleteUser(userData);

		// Adding Message that User Deleted Successfully :)
		Message message = Message.builder().content("Your Deleted Successful by Client :)").type(MessageType.green)
				.build();
		session.setAttribute("message", message);

		return "redirect:/register";
	}

	// Delete User Admin Handler----->
	@GetMapping("/do-deleteadmin/{userId}")
	public String deleteUserByAdmin(@PathVariable("userId") Long userId, Model model, HttpSession session) {

		System.out.println("deleteUser Handler..........");
		Optional<User> userOptional = this.userService.getUserById(userId);
		User userData = userOptional.get();

		// delete the user
		userService.deleteUser(userData);

		// Adding Message that User Deleted Successfully :)
		Message message = Message.builder().content("User Deleted Successful by Admin :)").type(MessageType.green)
				.build();
		session.setAttribute("message", message);

		return "redirect:/user/userlist";
	}

	// Delete User Handler----->
	@GetMapping("/do-togglesubscriptionisactive/{userId}")
	public String toggleSubscriptionIsActive(@PathVariable("userId") Long userId, Model model) {

		System.out.println("ToggleSubscriptionIsActive Handler..........");
		Optional<User> userOptional = this.userService.getUserById(userId);
		User userData = userOptional.get();

		System.out.println("Before- userData.isSubscriptionIsActive(): " + userData.isSubscriptionIsActive());
		if (userData.isSubscriptionIsActive()) {
			System.out.println("insdie-true userData.isSubscriptionIsActive(): " + userData.isSubscriptionIsActive());
			userData.setSubscriptionIsActive(false);
		} else {
			System.out.println("insdie-false userData.isSubscriptionIsActive(): " + userData.isSubscriptionIsActive());
			userData.setSubscriptionIsActive(true);
		}
		System.out.println("After- userData.isSubscriptionIsActive(): " + userData.isSubscriptionIsActive());

		// Update subscriptionIsActive the user
		userService.updateUser(userData);

		return "redirect:/user/userlist";
	}

}
