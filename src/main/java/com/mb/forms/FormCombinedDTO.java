package com.mb.forms;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.mb.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FormCombinedDTO {

	@NotBlank(message = "Username is required")
	@Size(min = 3, message = "Min 3 Characters is required")
	private String name;

	@Email(message = "Invalid Email Address")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Min 6 Characters is required")
	private String password;

	@NotBlank(message = "PhoneNumber is required")
	@Size(min = 8, max = 12, message = "Invalid Phone Number")
	private String phoneNumber;

	@NotBlank(message = "Gender is required")
	private String gender;

	@NotBlank(message = "Religion is required")
	private String religion; // hindu, sikh, muslium
	@NotBlank(message = "Caste is required")
	private String caste; // ramgharia, suri, jatt

//	@NotBlank(message = "MinAge is required")
//	@Min(value = 19, message = "Please fill Min. & Max. Age as follow")
	private int minAge;
	@NotNull(message = "Age is required")
	@Range(min = 18, max = 60, message = "Age must be between 18 and 100")
	private int age;
//	@NotBlank(message = "MaxAge is required")
//	@Max(value = 60, message = "Please fill Min. & Max. Age+ as follow")
	private int maxAge;

//	@NotBlank(message = "MinHeight is required")
//	@Min(value = 4, message = "Please fill Min. & Max. Height as follow")
	private int minHeight;
	@NotNull(message = "Height is required")
	@Range(min = 4, max = 7, message = "Height must be between 4 and 7")
	private int height;
//	@NotBlank(message = "MaxHeight is required")
//	@Max(value = 7, message = "Please fill Min. & Max. Height+ as follow")
	private int maxHeight;

	@NotBlank(message = "MarriedStatus is required")
	private String marriedStatus;
//	@NotBlank(message = "Place is required")
	private String place;
	@NotBlank(message = "Occupation is required")
	private String occupation;

//	@Getter(value = AccessLevel.NONE)
	private boolean enabled = true;
}