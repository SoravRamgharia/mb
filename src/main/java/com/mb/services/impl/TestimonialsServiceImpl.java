package com.mb.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.entities.Testimonials;
import com.mb.entities.User;
import com.mb.services.TestimonialsService;
import com.mb.repositories.TestimonialsRepo;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {
	
	@Autowired
	private TestimonialsRepo testimonialsRepo;

	@Override
	public Testimonials saveTestimonials(Testimonials testimonials) {
		return testimonialsRepo.save(testimonials);
	}

	@Override
	public List<Testimonials> getAllTestimonials() {
		return testimonialsRepo.findAll();
	}

}
