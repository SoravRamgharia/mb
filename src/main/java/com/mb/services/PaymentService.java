package com.mb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mb.entities.PaymentResponse;
import com.mb.entities.User;
import com.mb.repositories.PaymentResponseRepo;
import com.mb.repositories.UserRepo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class PaymentService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PaymentResponseRepo paymentResponseRepo;

	public Optional<PaymentResponse> getUserById(Long id) {
		return paymentResponseRepo.findById(id);
	}

	public List<PaymentResponse> getAllPaymentResponses() {
		return paymentResponseRepo.findAll();
	}

	public void storePaymentResponse(Long userId, PaymentResponse paymentResponse) {
		String query = "INSERT INTO payments (user_id, razorpay_payment_id, razorpay_order_id, razorpay_signature, validity_period, validity_type) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(query, userId, paymentResponse.getRazorpayPaymentId(), paymentResponse.getRazorpayOrderId(),
				paymentResponse.getRazorpaySignature(), paymentResponse.getValidityPeriod(),
				paymentResponse.getValidityType());
	}

	public boolean verifyPaymentSignature(PaymentResponse paymentResponse, String secretKey) {
		try {
			String razorpayOrderId = paymentResponse.getRazorpayOrderId();
			String razorpayPaymentId = paymentResponse.getRazorpayPaymentId();
			String razorpaySignature = paymentResponse.getRazorpaySignature();

			String dataToHash = razorpayOrderId + "|" + razorpayPaymentId;
			byte[] hashBytes = hmacSHA256(dataToHash, secretKey);
			String generatedSignature = bytesToHex(hashBytes);

			return generatedSignature.equals(razorpaySignature);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			// Log the error or handle it accordingly
			System.err.println("Error verifying payment signature: " + e.getMessage());
			return false;
		}
	}

	public boolean verifyPaymentSignatureAuthenticate(PaymentResponse paymentResponse, String secretKey) {
		try {
			String razorpayOrderId = paymentResponse.getRazorpayOrderId();
			String razorpayPaymentId = paymentResponse.getRazorpayPaymentId();
			String razorpaySignature = paymentResponse.getRazorpaySignature();

			String dataToHash = razorpayOrderId + "|" + razorpayPaymentId;
			byte[] hashBytes = hmacSHA256(dataToHash, secretKey);
			String generatedSignature = bytesToHex(hashBytes);

			return generatedSignature.equals(razorpaySignature);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			// Log the error or handle it accordingly
			System.err.println("Error verifying payment signature: " + e.getMessage());
			return false;
		}
	}

	private byte[] hmacSHA256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
		return mac.doFinal(data.getBytes());
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}