package com.mb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mb.entities.PaymentResponse;

@Repository
public interface PaymentResponseRepo extends JpaRepository<PaymentResponse, Long> {
	// Extra Methods DB Related Operations

	// Custom Finder Methods...
	Optional<PaymentResponse> findById(Long userId);

	List<PaymentResponse> findAll();

}