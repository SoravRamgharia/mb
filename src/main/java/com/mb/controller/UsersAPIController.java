package com.mb.controller;

import com.mb.entities.User;
import com.mb.helpers.FileCRUD;
import com.mb.helpers.Helper;
import com.mb.services.UserService;
import com.mb.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
//@CrossOrigin("*")
public class UsersAPIController {

	@Autowired
	private UserService userService;

//	@PostMapping("/userfile/upload")
//	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
//		if (FileCRUD.checkExcelFormat(file)) {
//			// true
//			this.userService.saveFile(file);
//
//			return ResponseEntity.ok(Map.of("message", "File is Uploaded and Data is Saved to Database :)"));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Excel File !!!");
//	}

	@PostMapping("/userfile/upload")
	public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("message", "File is empty!"));
		}

		if (FileCRUD.checkExcelFormat(file)) {
			this.userService.saveFile(file);
			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database :)"));
		}

		return ResponseEntity.badRequest().body(Map.of("message", "Please upload a valid Excel file!"));
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Excel File !!!");
	}

//	@GetMapping("/getalluser")
//	public List<User> getAlluser() {
//		return this.userService.getAllUsers();
//	}

	@GetMapping("/getalluser")
	public ResponseEntity<?> getAllUsers(Authentication authentication) {
		// Illegal Access Handler
		String username = Helper.getEmailOfLoggedInUser(authentication);
		User userData = userService.getUserByEmail(username);

		// Check if the user is authorized
		if (!userData.getEmail().equals("admin@gmail.com")) {
			// Return a redirect response for web applications
			return ResponseEntity.status(HttpStatus.FOUND) // 302 Found
					.header("Location", "/notauthorizedaccess").build();
		}

		List<User> users = this.userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/export/users")
	public ResponseEntity<byte[]> exportUsersToExcel() {
		List<User> users = userService.getAllUsers();

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			Sheet sheet = workbook.createSheet("Users");

			// Create header row
			Row headerRow = sheet.createRow(0);
			String[] headers = { "user_id", "address", "age", "any_demand", "any_remarks", "brith_time", "caste",
					"date_of_birth", "email", "family_status", "father_job_salary", "father_job_title", "father_name",
					"father_occupation", "form_filled_by", "gender", "height", "married_status", "max_age",
					"max_height", "min_age", "min_height", "mother_job_salary", "mother_job_title", "mother_name",
					"mother_occupation", "name", "nri_place", "occupation", "password", "phone_number1",
					"phone_number2", "picture", "place", "qualification", "qualification_field", "razorpay_signature",
					"religion", "subcaste", "subscription_is_active", "total_brothers", "total_family_members",
					"total_sisters", "your_job_salary", "your_job_title" };

			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Populate data
			int rowNum = 1;
			for (User user : users) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(user.getUserId() != null ? user.getUserId() : 0);
				row.createCell(1).setCellValue(user.getAddress() != null ? user.getAddress() : "Not Mention");
				row.createCell(2).setCellValue(user.getAge() != null ? user.getAge() : 0);
				row.createCell(3).setCellValue(user.getAnyDemand() != null ? user.getAnyDemand() : "Not Mention");
				row.createCell(4).setCellValue(user.getAnyRemarks() != null ? user.getAnyRemarks() : "Not Mention");
				row.createCell(5).setCellValue(user.getBrithTime() != null ? user.getBrithTime() : "Not Mention");
				row.createCell(6).setCellValue(user.getCaste() != null ? user.getCaste() : "Not Mention");
				row.createCell(7).setCellValue(user.getDateOfBirth() != null ? user.getDateOfBirth() : "Not Mention");
				row.createCell(8).setCellValue(user.getEmail() != null ? user.getEmail() : "Not Mention");
				row.createCell(9).setCellValue(user.getFamilyStatus() != null ? user.getFamilyStatus() : "Not Mention");
				row.createCell(10).setCellValue(user.getFatherJobSalary() != null ? user.getFatherJobSalary() : 0);
				row.createCell(11)
						.setCellValue(user.getFatherJobTitle() != null ? user.getFatherJobTitle() : "Not Mention");
				row.createCell(12).setCellValue(user.getFatherName() != null ? user.getFatherName() : "Not Mention");
				row.createCell(13)
						.setCellValue(user.getFatherOccupation() != null ? user.getFatherOccupation() : "Not Mention");
				row.createCell(14)
						.setCellValue(user.getFormFilledBy() != null ? user.getFormFilledBy() : "Not Mention");
				row.createCell(15).setCellValue(user.getGender() != null ? user.getGender() : "Not Mention");
				row.createCell(16).setCellValue(user.getHeight() != null ? user.getHeight() : 0);
				row.createCell(17)
						.setCellValue(user.getMarriedStatus() != null ? user.getMarriedStatus() : "Not Mention");
				row.createCell(18).setCellValue(user.getMaxAge() != 0 ? user.getMaxAge() : 0);
				row.createCell(19).setCellValue(user.getMaxHeight() != 0 ? user.getMaxHeight() : 0);
				row.createCell(20).setCellValue(user.getMinAge() != 0 ? user.getMinAge() : 0);
				row.createCell(21).setCellValue(user.getMinHeight() != 0 ? user.getMinHeight() : 0);
				row.createCell(22).setCellValue(user.getMotherJobSalary() != null ? user.getMotherJobSalary() : 0);
				row.createCell(23)
						.setCellValue(user.getMotherJobTitle() != null ? user.getMotherJobTitle() : "Not Mention");
				row.createCell(24).setCellValue(user.getMotherName() != null ? user.getMotherName() : "Not Mention");
				row.createCell(25)
						.setCellValue(user.getMotherOccupation() != null ? user.getMotherOccupation() : "Not Mention");
				row.createCell(26).setCellValue(user.getName() != null ? user.getName() : "Not Mention");
				row.createCell(27).setCellValue(user.getNriPlace() != null ? user.getNriPlace() : "Not Mention");
				row.createCell(28).setCellValue(user.getOccupation() != null ? user.getOccupation() : "Not Mention");
				row.createCell(29).setCellValue(user.getPassword() != null ? user.getPassword() : "Not Mention");
				row.createCell(30)
						.setCellValue(user.getPhoneNumber1() != null ? user.getPhoneNumber1() : "Not Mention");
				row.createCell(31)
						.setCellValue(user.getPhoneNumber2() != null ? user.getPhoneNumber2() : "Not Mention");
				row.createCell(32).setCellValue(user.getPicture() != null ? user.getPicture()
						: "https://res.cloudinary.com/dnhvlqc1n/image/upload/v1726864762/Image-removebg_s6ngqu.png");
				row.createCell(33).setCellValue(user.getPlace() != null ? user.getPlace() : "Not Mention");
				row.createCell(34)
						.setCellValue(user.getQualification() != null ? user.getQualification() : "Not Mention");
				row.createCell(35).setCellValue(
						user.getQualificationField() != null ? user.getQualificationField() : "Not Mention");
				row.createCell(36)
						.setCellValue(user.getRazorpaySignature() != null ? user.getRazorpaySignature() : "NULL");
				row.createCell(37).setCellValue(user.getReligion() != null ? user.getReligion() : "Not Mention");
				row.createCell(38).setCellValue(user.getSubcaste() != null ? user.getSubcaste() : "Not Mention");
				row.createCell(39).setCellValue(user.isSubscriptionIsActive());
				row.createCell(40).setCellValue(user.getTotalBrothers() != 0 ? user.getTotalBrothers() : 0);
				row.createCell(41).setCellValue(user.getTotalFamilyMembers() != 0 ? user.getTotalFamilyMembers() : 0);
				row.createCell(42).setCellValue(user.getTotalSisters() != 0 ? user.getTotalSisters() : 0);
				row.createCell(43).setCellValue(user.getYourJobSalary() != null ? user.getYourJobSalary() : 0);
				row.createCell(44)
						.setCellValue(user.getYourJobTitle() != null ? user.getYourJobTitle() : "Not Mention");
			}

			workbook.write(outputStream);
			// Assuming outputStream has been previously defined
			byte[] byteArray = outputStream.toByteArray();

			// Get current date and time for filename
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")); 
			String filename = "MBusers_" + date + "_" + time + ".xlsx";
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

			return ResponseEntity.ok().headers(httpHeaders).body(byteArray);

		} catch (Exception e) {
			e.printStackTrace(); // Log the error
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private CellStyle createHeaderStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		return style;
	}

}
