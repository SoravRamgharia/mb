package com.mb.controller;

import com.mb.entities.User;
import com.mb.helpers.FileCRUD;
import com.mb.helpers.Helper;
import com.mb.services.UserService;
import com.mb.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin("*")
public class UsersAPIController {

	@Autowired
	private UserService userService;

	@PostMapping("/userfile/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		if (FileCRUD.checkExcelFormat(file)) {
			// true
			this.userService.saveFile(file);

			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
	}

	@GetMapping("/getalluser")
	public List<User> getAlluser() {
		return this.userService.getAllUsers();
	}

}
