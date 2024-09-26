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

		Page<User> pageContent = userService.findMatchUserDetailsByFilter(user, page, size, sortBy, direction);

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("foundTotalMatches", pageContent.getTotalElements());
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("userFormDetails", userFormDetails);

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
			@RequestParam(value = "marriedStatus", required = true) String marriedStatus,
			@RequestParam(value = "place", required = false) String place,
			@RequestParam(value = "occupation", required = false) String occupation, Model model,
			Authentication authentication) throws Exception {

		// Fetch Form-Data from UserForm to bind with Model_Object by @ModelAttribute
		System.out.println("Processing Process user/do-findmatch Handler...");

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

		Page<User> pageContent = userService.findMatchUserDetailsByFilter(user, page, size, sortBy, direction);

		Optional<Authentication> authOptional = Optional.ofNullable(authentication);
		if (authOptional.isPresent()) {
			String username = Helper.getEmailOfLoggedInUser(authOptional.get());
			User userData = userService.getUserByEmail(username);
			model.addAttribute("isSubscriptionIsActive", userData.isSubscriptionIsActive());
		} else {
			return "redirect:/paymentplans"; // replace with your actual pay link URL
		}

		model.addAttribute("foundTotalMatches", pageContent.getTotalElements());

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("userFormDetails", userFormDetails);

		if (pageContent.isEmpty()) {
			return "/matchedusernotfound";
		}

		return "User/matcheduserlist";
	}

	@RequestMapping("/user/userlist")
	public String userList(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication authentication) {

		// Illegal Access Handler
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		if (!userData.getEmail().equals(adminEmail)) {
			return "NotAuthorizedAccess";
		}

		Page<User> pageContent = userService.getByUser(page, size, sortBy, direction);

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

		int totalPages = pageContent.getTotalPages();
		int currentPage = pageContent.getNumber();
		int pageWindow = 5; // How many pages to display in the pagination window

		int startPage = Math.max(1, currentPage - (pageWindow / 2));
		int endPage = Math.min(totalPages, currentPage + (pageWindow / 2));

		// Ensure there are always 5 pages displayed, adjusting start or end if
		// necessary
		if (endPage - startPage + 1 < pageWindow) {
			if (startPage == 1) {
				endPage = Math.min(pageWindow, totalPages);
			} else if (endPage == totalPages) {
				startPage = Math.max(1, totalPages - pageWindow + 1);
			}
		}

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);

		List<User> users = userService.getAllUsers();
		if (users.isEmpty()) {
			model.addAttribute("users", Collections.emptyList());
		} else {
			model.addAttribute("users", users);
			model.addAttribute("adminEmail", adminEmail);
		}

		return "User/userlist";
	}

}
