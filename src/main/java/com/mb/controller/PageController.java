package com.mb.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mb.forms.UserForm;
import com.mb.forms.UserFormDetails;
import com.mb.helpers.Helper;
import com.mb.helpers.Message;
import com.mb.helpers.MessageType;
import com.mb.services.TestimonialsService;
import com.mb.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import com.mb.entities.Testimonials;
import com.mb.entities.User;

@Controller
public class PageController {

	@Autowired
	private TestimonialsService testimonialsService;

	@Autowired
	private UserService userService;

	// Open Home Page Handler.....
	@RequestMapping("/")
	public String startFromHere() {
//		return "redirect:/findmatch";
		return "index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/home")
	public String home() {
		return "index";
	}

	@RequestMapping("/base")
	public String base(Authentication authentication, Model model) {
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		model.addAttribute("userData", userData);

		return "base";
	}

	@RequestMapping("/gallery")
	public String Gallery() {
		return "index";
////    Add gallery data to the model.....
//	    List<GalleryImage> galleryImages = // retrieve gallery images from database or any other source
//	    model.addAttribute("galleryImages", galleryImages);
//	    return "index";

//	<div class="gallery">
//	    <div th:each="image : ${galleryImages}">
//	        <img th:src="${image.url}" alt="${image.alt}">
//	    </div>
//	</div>
	}

	@RequestMapping("/service")
	public String service() {
		return "service";
	}

	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}

	@RequestMapping("/paidSuccessfully")
	public String paidSuccessfully() {
		return "paidSuccessfully";
	}

	@RequestMapping("/paymentplans")
	public String paymentplans(Model model) {
		return "paymentplans";
	}

	@RequestMapping("/user/paymentplans")
	public String userPaymentplans(Model model) {
		return "User/paymentplans";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/notauthorizedaccess")
	public String NotAuthorizedAccess() {
		return "NotAuthorizedAccess";
	}

	@RequestMapping("/logintopay")
	public String logintopay(HttpSession session) {
		// Adding Message that Register Successfully :)
		Message message = Message.builder().content("Login (Register if Needed)  to Access Payment-Plans")
				.type(MessageType.green).build();
		session.setAttribute("message", message);

		return "login";
	}

//  Open for Register Page Handler----->
	@RequestMapping("/register")
	public String registeration(Model model) {
		System.out.println("Opening Registeration Handler...");

		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);

		return "register";
	}

	@RequestMapping("/testimonials")
	public String Testimonials(Model model) {

		List<Testimonials> testimonialsAll = testimonialsService.getAllTestimonials();
		// Reverse the list
		Collections.reverse(testimonialsAll);

		model.addAttribute("testimonials", testimonialsAll);

		return "testimonials";
	}

	@PostMapping("/do-testimonials")
	public String processTestimonials(@RequestParam("name") String name, @RequestParam("review") String review,
			@RequestParam(value = "rating", defaultValue = "5") int rating, Model model, HttpSession session) {

//		if (bindingResult.hasErrors()) {
//			System.out.println("\n ---> processRegisteration\n" + bindingResult.toString());
//			return "Testimonials";
//		}

		Testimonials testimonials = new Testimonials();

		testimonials.setName(name);
		testimonials.setReview(review);
		testimonials.setRating(rating);

		Testimonials savedTestimonials = testimonialsService.saveTestimonials(testimonials);

		// Adding Message that Register Successfully :)
		Message message = Message.builder().content("Your Review Add Successful :)").type(MessageType.green).build();
		session.setAttribute("message", message);

		return "redirect:/testimonials";
	}

}