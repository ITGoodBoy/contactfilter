package com.celestialapps.contactfilter;

import com.celestialapps.contactfilter.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactfilterApplication  {



	public static void main(String[] args) {
		SpringApplication.run(ContactfilterApplication.class, args);
	}

}
