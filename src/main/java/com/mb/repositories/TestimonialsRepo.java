package com.mb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.entities.Testimonials;
import com.mb.entities.User;

public interface TestimonialsRepo extends JpaRepository<Testimonials, Integer> {

//	@EntityGraph(attributePaths = { "review_id", "name", "rating", "review" })
//	List<Testimonials> findAll(Testimonials testimonials);


//    @EntityGraph(attributePaths = { "review_id", "name", "rating", "review" })
//    List<Testimonials> findAllWithAttributes();
    
//    List<Testimonials> findAllByOrderByNameAsc();
    List<Testimonials> findAll();

}
