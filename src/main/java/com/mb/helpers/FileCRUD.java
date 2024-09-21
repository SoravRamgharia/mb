package com.mb.helpers;

import com.mb.entities.PaymentResponse;
import com.mb.entities.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileCRUD {

	// Check that File is of Excel Type or Not ?
	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}

	}

	// Convert Excel to List of Users
	public static List<User> convertExcelToListOfUser(InputStream is) {
		List<User> list = new ArrayList<>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0); // Get the first sheet

			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			DataFormatter formatter = new DataFormatter();

			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) { // Skip header row
					rowNumber++;
					continue;
				}

				User p = new User();
				boolean isValidRow = true;
				int cid = 0;

				Iterator<Cell> cells = row.iterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					String cellValue = formatter.formatCellValue(cell);

					try {
						switch (cid) {
						case 0:
							p.setUserId(Long.parseLong(cellValue));
							break;
						case 1:
							p.setAddress(cellValue);
							break;
						case 2:
							// Left empty for age calculation later
							break;
						case 3:
							p.setAnyDemand(cellValue);
							break;
						case 4:
							p.setAnyRemarks(cellValue);
							break;
						case 5:
							p.setBrithTime(cellValue);
							break;
						case 6:
							p.setCaste(cellValue);
							break;
						case 7:
							// Calculate Age from Date of Birth
							String dateOfBirth = cellValue;
							DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
							LocalDate dob = LocalDate.parse(dateOfBirth, dateTimeFormatter);
							p.setDateOfBirth(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
							p.setAge(Period.between(dob, LocalDate.now()).getYears());
							break;
						case 8:
							p.setEmail(cellValue);
							break;
						case 9:
							p.setFamilyStatus(cellValue);
							break;
						case 10:
							p.setFatherJobSalary(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 11:
							p.setFatherJobTitle(cellValue);
							break;
						case 12:
							p.setFatherName(cellValue);
							break;
						case 13:
							p.setFatherOccupation(cellValue);
							break;
						case 14:
							p.setFormFilledBy(cellValue);
							break;
						case 15:
							p.setGender(cellValue);
							break;
						case 16:
							p.setHeight(Double.parseDouble(cellValue));
							break;
						case 17:
							p.setMarriedStatus(cellValue);
							break;
						case 18:
							p.setMaxAge(Integer.parseInt(cellValue));
							break;
						case 19:
							p.setMaxHeight(Integer.parseInt(cellValue));
							break;
						case 20:
							p.setMinAge(Integer.parseInt(cellValue));
							break;
						case 21:
							p.setMinHeight(Integer.parseInt(cellValue));
							break;
						case 22:
							p.setMotherJobSalary(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 23:
							p.setMotherJobTitle(cellValue);
							break;
						case 24:
							p.setMotherName(cellValue);
							break;
						case 25:
							p.setMotherOccupation(cellValue);
							break;
						case 26:
							p.setName(cellValue);
							break;
						case 27:
							p.setNriPlace(cellValue);
							break;
						case 28:
							p.setOccupation(cellValue);
							break;
						case 29:
							p.setPassword(cellValue);
							break;
						case 30:
							p.setPhoneNumber1(cellValue);
							break;
						case 31:
							p.setPhoneNumber2(cellValue);
							break;
						case 32:
							p.setPicture(cellValue);
							List<String> imageUrls = new ArrayList<>();
							imageUrls.add(cellValue);
							p.setImages(imageUrls);
							break;
						case 33:
							p.setPlace(cellValue);
							break;
						case 34:
							p.setQualification(cellValue);
							break;
						case 35:
							p.setQualificationField(cellValue);
							break;
						case 36:
							p.setRazorpaySignature(cellValue);
							break;
						case 37:
							p.setReligion(cellValue);
							break;
						case 38:
							p.setSubcaste(cellValue);
							break;
						case 39:
							p.setSubscriptionIsActive(Boolean.parseBoolean(cellValue));
							break;
						case 40:
							p.setTotalBrothers(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 41:
							p.setTotalFamilyMembers(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 42:
							p.setTotalSisters(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 43:
							p.setYourJobSalary(cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue));
							break;
						case 44:
							p.setYourJobTitle(cellValue);
							break;
						default:
							break;
						}
					} catch (NumberFormatException nfe) {
						System.err.printf("Row %d, Column %d: Invalid number format for value '%s' - %s%n",
								rowNumber + 1, cid + 1, cellValue, nfe.getMessage());
						isValidRow = false;
					} catch (DateTimeParseException dtpe) {
						System.err.printf("Row %d, Column %d: Invalid date format for value '%s' - %s%n", rowNumber + 1,
								cid + 1, cellValue, dtpe.getMessage());
						isValidRow = false;
					} catch (Exception e) {
						System.err.printf("Row %d, Column %d: Error processing value '%s' - %s%n", rowNumber + 1,
								cid + 1, cellValue, e.getMessage());
						isValidRow = false;
					}
					cid++;
				}

				if (isValidRow) {
					list.add(p);
				} else {
					System.err.printf("Row %d: Skipped due to validation errors.%n", rowNumber + 1);
				}
				rowNumber++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}