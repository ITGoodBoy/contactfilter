package com.celestialapps.contactfilter;

import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.model.MongoContact;
import com.celestialapps.contactfilter.repository.ContactRepository;
import com.celestialapps.contactfilter.repository.MongoContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootApplication
public class ContactfilterApplication {





    public static void main(String[] args) {
		SpringApplication.run(ContactfilterApplication.class, args);
	}



}
