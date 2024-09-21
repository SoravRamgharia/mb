package com.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mb.config.AppConfig;
import com.mb.entities.User;
import com.mb.helpers.AppConstants;
import com.mb.repositories.UserRepo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.GenerationType;

import com.mb.exception.GlobalExceptionHandler;

@SpringBootApplication
@EnableScheduling
public class MarriageBureauApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MarriageBureauApplication.class, args);
        System.out.println("<=====: This Spring Boot Website Developed by MANPREET SINGH (9592297120) :=====>");
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder appPasswordEncoder;
    
	@Value("${admin.email}")
	private String adminEmail;


    @PostConstruct
    public void createAdmin() throws Exception {
        User user = new User();
        List<String> pic = new ArrayList<>();
        pic.add("img/Imagebg.png");

        user.setName("Admin");
        user.setEmail(adminEmail);
        user.setPassword(appPasswordEncoder.encode("open"));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setPicture(pic.get(0));
        user.setImages(pic);
        user.setGender("Male");
        user.setReligion("Sikh");
        user.setCaste("ramgharia");
        user.setDateOfBirth("15/11/1999");
        user.setHeight(6.6);
        user.setPlace("indian");
        user.setMarriedStatus("Married");
        user.setQualification("Under-Graduate");
        user.setOccupation("Business");
        user.setFamilyStatus("Moderate");
        user.setTotalFamilyMembers(5);
        user.setTotalBrothers(2);
        user.setTotalSisters(1);
        user.setPhoneNumber1("1234567890");
        user.setFormFilledBy("Self");
        
        userRepo.findByEmail(adminEmail).ifPresentOrElse(user1 -> {
        }, () -> {
            userRepo.save(user);
            System.out.println("<==========: Admin has Created Succesfully :==========>");
        });
    }
}