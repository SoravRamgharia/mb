package com.mb.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;

//	@Column(name = "user_name", nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String email;

	@Getter(AccessLevel.NONE)
	private String password;

//	User Images...
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> images = new ArrayList<>();
	private String picture;

//	@ElementCollection(fetch = FetchType.EAGER)
//	private List<String> CloudinaryImagePublicIds = new ArrayList<>();
//	private String cloudinaryImagePublicId;

	private String gender; // male, female

	private String religion; // hindu, sikh, muslium
	private String caste; // ramgharia, suri, jatt
	private String subcaste; // Bhamra...

	private int minAge;
//	private int age;
	private int maxAge;

	private String dateOfBirth;
	private Integer age;
	private String brithTime;

	private int minHeight;
	private Double height;
	private int maxHeight;

	private String marriedStatus;
	private String place;
	private String nriPlace;

	private String qualification;
	private String qualificationField;

	private String occupation;
	private String yourJobTitle;
	private Integer yourJobSalary;

	private String familyStatus;
	private Integer totalFamilyMembers;
	private Integer totalBrothers;
	private Integer totalSisters;

	private String fatherName;
	private String fatherOccupation;
	private String fatherJobTitle;
	private Integer fatherJobSalary;

	private String motherName;
	private String motherOccupation;
	private String motherJobTitle;
	private Integer motherJobSalary;

	private String anyDemand;
	private String anyRemarks;
	private String address;

	private String phoneNumber1;
	private String phoneNumber2;

	private String formFilledBy;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// list of roles[USER,ADMIN]
		// Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
		Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return roles;
	}

//	@Getter(value = AccessLevel.NONE)
//	@Setter(value = AccessLevel.NONE)
	private boolean subscriptionIsActive;

//	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
////	@JoinColumn(name = "payment_response_id")
//	@JoinColumn()
//	private PaymentResponse paymentResponse;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn()
	@PrimaryKeyJoinColumn
	private PaymentResponse paymentResponse;

	private String razorpaySignature;

	// for this project:
	// email id hai wahi hamare username
	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", images="
				+ images + ", picture=" + picture + ", gender=" + gender + ", religion=" + religion + ", caste=" + caste
				+ ", subcaste=" + subcaste + ", minAge=" + minAge + ", maxAge=" + maxAge + ", dateOfBirth="
				+ dateOfBirth + ", age=" + age + ", brithTime=" + brithTime + ", minHeight=" + minHeight + ", height="
				+ height + ", maxHeight=" + maxHeight + ", marriedStatus=" + marriedStatus + ", place=" + place
				+ ", nriPlace=" + nriPlace + ", qualification=" + qualification + ", qualificationField="
				+ qualificationField + ", occupation=" + occupation + ", yourJobTitle=" + yourJobTitle
				+ ", yourJobSalary=" + yourJobSalary + ", familyStatus=" + familyStatus + ", totalFamilyMembers="
				+ totalFamilyMembers + ", totalBrothers=" + totalBrothers + ", totalSisters=" + totalSisters
				+ ", fatherName=" + fatherName + ", fatherOccupation=" + fatherOccupation + ", fatherJobTitle="
				+ fatherJobTitle + ", fatherJobSalary=" + fatherJobSalary + ", motherName=" + motherName
				+ ", motherOccupation=" + motherOccupation + ", motherJobTitle=" + motherJobTitle + ", motherJobSalary="
				+ motherJobSalary + ", anyDemand=" + anyDemand + ", anyRemarks=" + anyRemarks + ", address=" + address
				+ ", phoneNumber1=" + phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", formFilledBy=" + formFilledBy
				+ ", roleList=" + roleList + "]";
	}

}
