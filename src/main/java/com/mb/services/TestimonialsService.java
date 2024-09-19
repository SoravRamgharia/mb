package com.mb.services;

import java.util.List;
import java.util.Optional;

import com.mb.entities.Testimonials;

public interface TestimonialsService {
	Testimonials saveTestimonials(Testimonials testimonials);

	List<Testimonials> getAllTestimonials();

//	Optional<Testimonials> getTestimonialsById(Long id);
//	Optional<Testimonials> updateTestimonials(Testimonials testimonials);
//	void deleteTestimonialsById(Long id);
//	public void deleteTestimonials(Testimonials testimonials);

}
