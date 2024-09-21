package com.mb.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mb.entities.User;
import com.mb.forms.UserFormDetails;
import com.mb.helpers.AppConstants;
import com.mb.services.UserService;
import com.mb.helpers.Helper;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@Controller
public class FindMatch {

	@Autowired
	private UserService userService;
	
	@Value("${admin.email}")
	private String adminEmail;


//  Open for FindMatch Page Handler----->
	@RequestMapping("/findmatch")
	public String findmatch(Model model) {
		System.out.println("Opening findMatch Handler...");

		UserFormDetails userFormDetails = new UserFormDetails();
		model.addAttribute("userFormDetails", userFormDetails);
		return "findmatch";
	}

	@RequestMapping("/user/findmatch")
	public String findmatchUser(Model model) {
		System.out.println("Opening findMatch Handler...");

		UserFormDetails userFormDetails = new UserFormDetails();
		model.addAttribute("userFormDetails", userFormDetails);
		return "User/findmatch";
	}

//  Processing for FindMatching Handler----->
	@RequestMapping("/do-findmatch")
	public String processFindmatch(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "religion", required = false) String religion,
			@RequestParam(value = "caste", required = false) String caste,
			@RequestParam(value = "minAge", required = false) Integer minAge,
			@RequestParam(value = "maxAge", required = false) Integer maxAge,
			@RequestParam(value = "minHeight", required = false) Integer minHeight,
			@RequestParam(value = "maxHeight", required = false) Integer maxHeight,
			@RequestParam(value = "marriedStatus", required = false) String marriedStatus,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "occupation", required = false) String occupation, Model model) throws Exception {

		// Fetch Form-Data from UserForm to bind with Model_Object by @ModelAttribute
		System.out.println("Processing Process do-findmatch Handler...");

		UserFormDetails userFormDetails = new UserFormDetails();
		userFormDetails.setGender(gender);
		userFormDetails.setReligion(religion);
		userFormDetails.setCaste(caste);
		userFormDetails.setMinAge(minAge);
		userFormDetails.setMaxAge(maxAge);
		userFormDetails.setMinHeight(minHeight);
		userFormDetails.setMaxHeight(maxHeight);
		userFormDetails.setMarriedStatus(marriedStatus);
		userFormDetails.setPlace(place);
		userFormDetails.setOccupation(occupation);

		// Save UserForm Data to Database [ UserForm -> Created_User -> Database ]
		// userId | gender | religion | caste | age | height | marriedStatus | place |
		// occupation
		User user = new User();
		user.setGender(userFormDetails.getGender());
		user.setReligion(userFormDetails.getReligion());
		user.setCaste(userFormDetails.getCaste());
		user.setMinAge(userFormDetails.getMinAge());
		user.setMaxAge(userFormDetails.getMaxAge());
		user.setMinHeight(userFormDetails.getMinHeight());
		user.setMaxHeight(userFormDetails.getMaxHeight());
		user.setMarriedStatus(userFormDetails.getMarriedStatus());
		user.setPlace(userFormDetails.getPlace());
		user.setOccupation(userFormDetails.getOccupation());

		System.out.println("\n\n\nThis is matchUser from users.... Process do-findmatch  Handler");
		Page<User> pageContent = userService.findMatchUserDetailsByFilter(user, page, size, sortBy, direction);
		System.out.println(pageContent); // Page 1 of 4 containing com.mb.entities.User instances
		System.out.println(pageContent.getPageable()); // Page request [number: 0, size 3, sort: userId: ASC]
		System.out.println(pageContent.getPageable().getPageNumber()); // 0 page no.
		System.out.println(pageContent.getPageable().getPageSize()); // 3 size
		System.out.println(pageContent.getContent()); // [com.mb.entities.User@1337f51e, com.mb.entities.User@1cf82dbc,
														// com.mb.entities.User@5acf9f4b]
		System.out.println(pageContent.getTotalPages()); // 4 page, as per all user, set by pageSize:3
		System.out.println(pageContent.getNumberOfElements()); // 3 on per page
		System.out.println(pageContent.getTotalElements()); // 12 is total user

//		if (authentication == null) {
//		    model.addAttribute("isSubscriptionIsActive", false);
//		    return "redirect:/paymentplans"; // replace with your actual pay link URL
//		} else {
//		    String username = Helper.getEmailOfLoggedInUser(authentication);
//		    User userData = userService.getUserByEmail(username);
//		    model.addAttribute("isSubscriptionIsActive", userData.isSubscriptionIsActive());
//		    System.out.println(userData.getUserId() + " =========================================================== "
//		    		+ userData.isSubscriptionIsActive() + " ===========================================================");
//		}

//		Optional<Authentication> authOptional = Optional.ofNullable(authentication);
//		if (authOptional.isPresent()) {
//			String username = Helper.getEmailOfLoggedInUser(authOptional.get());
//			User userData = userService.getUserByEmail(username);
//			model.addAttribute("isSubscriptionIsActive", userData.isSubscriptionIsActive());
//			System.out.println(userData.getUserId() + " =========================================================== "
//					+ userData.isSubscriptionIsActive()
//					+ " ===========================================================");
//		} else {
//			return "redirect:/paymentplans"; // replace with your actual pay link URL
//		}

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("userFormDetails", userFormDetails);

		System.out.println(pageContent); // Page 1 of 1 containing com.mb.entities.User instances
		for (User u : pageContent) {
			System.out.println("User ID: " + u.getUserId() + ", Gender: " + u.getGender() + ", Religion: "
					+ u.getReligion() + ", Caste: " + u.getCaste() + ", Age: " + u.getAge() + ", MinAge: "
					+ u.getMinAge() + ", MaxAge: " + u.getMaxAge() + ", MinHeight: " + u.getMinHeight() + ", Height: "
					+ u.getHeight() + ", MaxHeight: " + u.getMaxHeight() + ", MarriedStatus: " + u.getMarriedStatus()
					+ ", Place: " + u.getPlace() + ", Occupation: " + u.getOccupation());
		}

		if (pageContent.isEmpty()) {
			return "/matchedusernotfound";
		}

		return "matcheduserlist";
	}

//  Processing for FindMatching Handler----->
	@RequestMapping("/user/do-findmatch")
	public String processFindmatchUser(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "religion", required = false) String religion,
			@RequestParam(value = "caste", required = false) String caste,
			@RequestParam(value = "minAge", required = false) Integer minAge,
			@RequestParam(value = "maxAge", required = false) Integer maxAge,
			@RequestParam(value = "minHeight", required = false) Integer minHeight,
			@RequestParam(value = "maxHeight", required = false) Integer maxHeight,
			@RequestParam(value = "marriedStatus", required = false) String marriedStatus,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "occupation", required = false) String occupation, Model model,
			Authentication authentication) throws Exception {

		// Fetch Form-Data from UserForm to bind with Model_Object by @ModelAttribute
		System.out.println("Processing Process do-findmatch Handler...");

		UserFormDetails userFormDetails = new UserFormDetails();
		userFormDetails.setGender(gender);
		userFormDetails.setReligion(religion);
		userFormDetails.setCaste(caste);
		userFormDetails.setMinAge(minAge);
		userFormDetails.setMaxAge(maxAge);
		userFormDetails.setMinHeight(minHeight);
		userFormDetails.setMaxHeight(maxHeight);
		userFormDetails.setMarriedStatus(marriedStatus);
		userFormDetails.setPlace(place);
		userFormDetails.setOccupation(occupation);

		// Save UserForm Data to Database [ UserForm -> Created_User -> Database ]
		// userId | gender | religion | caste | age | height | marriedStatus | place |
		// occupation
		User user = new User();
		user.setGender(userFormDetails.getGender());
		user.setReligion(userFormDetails.getReligion());
		user.setCaste(userFormDetails.getCaste());
		user.setMinAge(userFormDetails.getMinAge());
		user.setMaxAge(userFormDetails.getMaxAge());
		user.setMinHeight(userFormDetails.getMinHeight());
		user.setMaxHeight(userFormDetails.getMaxHeight());
		user.setMarriedStatus(userFormDetails.getMarriedStatus());
		user.setPlace(userFormDetails.getPlace());
		user.setOccupation(userFormDetails.getOccupation());

		System.out.println("\n\n\nThis is matchUser from users.... Process do-findmatch  Handler");
		Page<User> pageContent = userService.findMatchUserDetailsByFilter(user, page, size, sortBy, direction);
		System.out.println(pageContent); // Page 1 of 4 containing com.mb.entities.User instances
		System.out.println(pageContent.getPageable()); // Page request [number: 0, size 3, sort: userId: ASC]
		System.out.println(pageContent.getPageable().getPageNumber()); // 0 page no.
		System.out.println(pageContent.getPageable().getPageSize()); // 3 size
		System.out.println(pageContent.getContent()); // [com.mb.entities.User@1337f51e, com.mb.entities.User@1cf82dbc,
		// com.mb.entities.User@5acf9f4b]
		System.out.println(pageContent.getTotalPages()); // 4 page, as per all user, set by pageSize:3
		System.out.println(pageContent.getNumberOfElements()); // 3 on per page
		System.out.println(pageContent.getTotalElements()); // 12 is total user

//		if (authentication == null) {
//		    model.addAttribute("isSubscriptionIsActive", false);
//		    return "redirect:/paymentplans"; // replace with your actual pay link URL
//		} else {
//		    String username = Helper.getEmailOfLoggedInUser(authentication);
//		    User userData = userService.getUserByEmail(username);
//		    model.addAttribute("isSubscriptionIsActive", userData.isSubscriptionIsActive());
//		    System.out.println(userData.getUserId() + " =========================================================== "
//		    		+ userData.isSubscriptionIsActive() + " ===========================================================");
//		}

		Optional<Authentication> authOptional = Optional.ofNullable(authentication);
		if (authOptional.isPresent()) {
			String username = Helper.getEmailOfLoggedInUser(authOptional.get());
			User userData = userService.getUserByEmail(username);
			model.addAttribute("isSubscriptionIsActive", userData.isSubscriptionIsActive());
			System.out.println(userData.getUserId() + " =========================================================== "
					+ userData.isSubscriptionIsActive()
					+ " ===========================================================");
		} else {
			return "redirect:/paymentplans"; // replace with your actual pay link URL
		}

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("userFormDetails", userFormDetails);

		System.out.println(pageContent); // Page 1 of 1 containing com.mb.entities.User instances
		for (User u : pageContent) {
			System.out.println("User ID: " + u.getUserId() + ", Gender: " + u.getGender() + ", Religion: "
					+ u.getReligion() + ", Caste: " + u.getCaste() + ", Age: " + u.getAge() + ", MinAge: "
					+ u.getMinAge() + ", MaxAge: " + u.getMaxAge() + ", MinHeight: " + u.getMinHeight() + ", Height: "
					+ u.getHeight() + ", MaxHeight: " + u.getMaxHeight() + ", MarriedStatus: " + u.getMarriedStatus()
					+ ", Place: " + u.getPlace() + ", Occupation: " + u.getOccupation());
		}

		if (pageContent.isEmpty()) {
			return "/matchedusernotfound";
		}

		return "User/matcheduserlist";
	}

//  Open for View of All UserList Page Handler----->
	@RequestMapping("/user/userlist")
	public String userList(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication authentication) {

		System.out.println("Opening userList Handler...");

//		Illegal Access Handler....
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		System.out.println("username----> " + username);
		System.out.println("userData.getEmail()----> " + userData.getEmail());

		if (!userData.getEmail().equals(adminEmail))
			return "NotAuthorizedAccess";

//		--------------------
		System.out.println("\n\n\nThis is userList from User.... userList Handler");
		Page<User> pageContent = userService.getByUser(page, size, sortBy, direction);
		System.out.println(pageContent); // Page 1 of 4 containing com.mb.entities.User instances
		System.out.println(pageContent.getPageable()); // Page request [number: 0, size 3, sort: userId: ASC]
		System.out.println(pageContent.getPageable().getPageNumber()); // 0 page no.
		System.out.println(pageContent.getPageable().getPageSize()); // 3 size
		System.out.println(pageContent.getContent()); // [com.mb.entities.User@1337f51e, com.mb.entities.User@1cf82dbc,
														// com.mb.entities.User@5acf9f4b]
		System.out.println(pageContent.getTotalPages()); // 4 page, as per all user, set by pageSize:3
		System.out.println(pageContent.getNumberOfElements()); // 3 on per page
		System.out.println(pageContent.getTotalElements()); // 12 is total user

//		System.out.println(pageContent.getContent());

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

		for (User user : pageContent) {
			System.out.println("User ID: " + user.getUserId() + ", Gender: " + user.getGender() + ", Religion: "
					+ user.getReligion() + ", Caste: " + user.getCaste() + ", Age: " + user.getMaxAge() + ", Height: "
					+ user.getMaxHeight() + ", MarriedStatus: " + user.getMarriedStatus() + ", Place: "
					+ user.getPlace() + ", Occupation: " + user.getOccupation());
		}
		System.out.println("\n\n\n");
//		--------------------

		List<User> users = userService.getAllUsers();
		System.out.println(users);

		for (User user : users) {
			System.out.println("User ID: " + user.getUserId() + ", Gender: " + user.getGender() + ", Religion: "
					+ user.getReligion() + ", Caste: " + user.getCaste() + ", Age: " + user.getMaxAge() + ", Height: "
					+ user.getMaxHeight() + ", MarriedStatus: " + user.getMarriedStatus() + ", Place: "
					+ user.getPlace() + ", Occupation: " + user.getOccupation());
		}

		if (users.isEmpty()) {
			model.addAttribute("users", Collections.emptyList());
		} else {
			model.addAttribute("users", users);
			model.addAttribute("adminEmail", adminEmail);
		}
		return "User/userlist";

	}

}
