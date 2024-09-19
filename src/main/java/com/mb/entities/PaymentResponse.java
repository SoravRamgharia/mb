package com.mb.entities;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;

@Entity(name = "payments")
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;

	private String razorpayPaymentId;
	private String razorpayOrderId;
	private String razorpaySignature;
	private String validityPeriod;
	private String validityType;

//	@OneToOne(mappedBy = "paymentResponse")
//	@JsonIgnore
//	private User user;

	@OneToOne(mappedBy = "paymentResponse")
	@JsonIgnore
	@PrimaryKeyJoinColumn
	private User user;


	private Timestamp createdAt;
	private Date expiryDate;

	public boolean isSubscriptionValid() {
		if (createdAt == null) {
			return false; // or throw an exception, depending on your requirements
		}
		int validityPeriod = Integer.parseInt(this.validityPeriod);
		String validityType = this.validityType;
//		int validityPeriod = 0;
//		String validityType = "days";

		System.out
				.println("<===============================: isSubscriptionValid :==================================>");
		System.out.println("validityPeriod: " + validityPeriod);
		System.out.println("validityType: " + validityType);

		// Calculate the expiry date based on the validity period and type
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.setTimeInMillis(this.createdAt.getTime());
		if (validityType.equalsIgnoreCase("days")) {
			expiryDate.add(Calendar.DAY_OF_YEAR, validityPeriod);
		} else if (validityType.equalsIgnoreCase("months")) {
			expiryDate.add(Calendar.MONTH, validityPeriod);
		} else if (validityType.equalsIgnoreCase("years")) {
			expiryDate.add(Calendar.YEAR, validityPeriod);
		}
		System.out.println("=====>expiryDate.getTime(): " + expiryDate.getTime() + " | new Date(): " + new Date());

		this.expiryDate = expiryDate.getTime();

		// Check if the current date is before the expiry date
		return new Date().before(expiryDate.getTime());
	}

	@Override
	public String toString() {
		return "PaymentResponse [userId=" + userId + ", razorpayPaymentId=" + razorpayPaymentId + ", razorpayOrderId="
				+ razorpayOrderId + ", razorpaySignature=" + razorpaySignature + ", validityPeriod=" + validityPeriod
				+ ", validityType=" + validityType + ", createdAt=" + createdAt + ", expiryDate=" + expiryDate + "]";
	}

}
