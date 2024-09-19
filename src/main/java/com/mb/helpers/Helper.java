package com.mb.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public class Helper {

	public static String getEmailOfLoggedInUser(Authentication authentication) {

		// agar email is password se login kiya hai to : email kai
		System.out.println("Getting data from local database");
		return authentication.getName();

	}
}
