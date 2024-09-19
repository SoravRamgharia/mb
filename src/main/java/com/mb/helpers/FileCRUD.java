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

	            if (rowNumber == 0) {
	                rowNumber++;
	                continue;
	            }

	            Iterator<Cell> cells = row.iterator();
	            int cid = 0;
	            User p = new User();
	            boolean isValidRow = false;

	            while (cells.hasNext()) {
	                Cell cell = cells.next();

	                String cellValue = formatter.formatCellValue(cell);

	                switch (cid) {
	                case 0:
	                    p.setUserId(Long.parseLong(cellValue));
	                    isValidRow = true;
	                    break;
	                case 1:
	                    p.setAddress(cellValue);
	                    isValidRow = true;
	                    break;
	                case 2:
	                    p.setAge(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 3:
	                    p.setAnyDemand(cellValue);
	                    isValidRow = true;
	                    break;
	                case 4:
	                    p.setAnyRemarks(cellValue);
	                    isValidRow = true;
	                    break;
	                case 5:
	                    p.setBrithTime(cellValue);
	                    isValidRow = true;
	                    break;
	                case 6:
	                    p.setCaste(cellValue);
	                    isValidRow = true;
	                    break;
	                case 7:
	                    p.setDateOfBirth(cellValue);
	                    isValidRow = true;
	                    break;
	                case 8:
	                    p.setEmail(cellValue);
	                    isValidRow = true;
	                    break;
	                case 9:
	                    p.setFamilyStatus(cellValue);
	                    isValidRow = true;
	                    break;
	                case 10:
	                    p.setFatherJobSalary(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 11:
	                    p.setFatherJobTitle(cellValue);
	                    isValidRow = true;
	                    break;
	                case 12:
	                    p.setFatherName(cellValue);
	                    isValidRow = true;
	                    break;
	                case 13:
	                    p.setFatherOccupation(cellValue);
	                    isValidRow = true;
	                    break;
	                case 14:
	                    p.setFormFilledBy(cellValue);
	                    isValidRow = true;
	                    break;
	                case 15:
	                    p.setGender(cellValue);
	                    isValidRow = true;
	                    break;
	                case 16:
	                    p.setHeight(Double.parseDouble(cellValue));
	                    isValidRow = true;
	                    break;
	                case 17:
	                    p.setMarriedStatus(cellValue);
	                    isValidRow = true;
	                    break;
	                case 18:
	                    p.setMaxAge(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 19:
	                    p.setMaxHeight(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 20:
	                    p.setMinAge(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 21:
	                    p.setMinHeight(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 22:
	                    p.setMotherJobSalary(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 23:
	                    p.setMotherJobTitle(cellValue);
	                    isValidRow = true;
	                    break;
	                case 24:
	                    p.setMotherName(cellValue);
	                    isValidRow = true;
	                    break;
	                case 25:
	                    p.setMotherOccupation(cellValue);
	                    isValidRow = true;
	                    break;
	                case 26:
	                    p.setName(cellValue);
	                    isValidRow = true;
	                    break;
	                case 27:
	                    p.setNriPlace(cellValue);
	                    isValidRow = true;
	                    break;
	                case 28:
	                    p.setOccupation(cellValue);
	                    isValidRow = true;
	                    break;
	                case 29:
	                    p.setPassword(cellValue);
	                    isValidRow = true;
	                    break;
	                case 30:
	                    p.setPhoneNumber1(cellValue);
	                    isValidRow = true;
	                    break;
	                case 31:
	                    p.setPhoneNumber2(cellValue);
	                    isValidRow = true;
	                    break;
	                case 32:
	                    p.setPicture(cellValue);
	                    List<String> imageUrls = new ArrayList<>();
	                    imageUrls.add(cellValue);
	                    p.setImages(imageUrls);
	                    isValidRow = true;
	                    break;
	                case 33:
	                    p.setPlace(cellValue);
	                    isValidRow = true;
	                    break;
	                case 34:
	                    p.setQualification(cellValue);
	                    isValidRow = true;
	                    break;
	                case 35:
	                    p.setQualificationField(cellValue);
	                    isValidRow = true;
	                    break;
	                case 36:
	                    p.setRazorpaySignature(cellValue);
	                    isValidRow = true;
	                    break;
	                case 37:
	                    p.setReligion(cellValue);
	                    isValidRow = true;
	                    break;
	                case 38:
	                    p.setSubcaste(cellValue);
	                    isValidRow = true;
	                    break;
	                case 39:
	                    p.setSubscriptionIsActive(Boolean.parseBoolean(cellValue));
	                    isValidRow = true;
	                    break;
	                case 40:
	                    p.setTotalBrothers(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 41:
	                    p.setTotalFamilyMembers(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 42:
	                    p.setTotalSisters(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 43:
	                    p.setYourJobSalary(Integer.parseInt(cellValue));
	                    isValidRow = true;
	                    break;
	                case 44:
	                    p.setYourJobTitle(cellValue);
	                    isValidRow = true;
	                    break;
	                default:
	                    break;
	            }
	                cid++;
	            }

	            if (isValidRow) {
	                list.add(p);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}