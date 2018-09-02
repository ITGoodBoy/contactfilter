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
public class ContactfilterApplication  implements CommandLineRunner{

  // first-names.txt contains 4945 records, entity size = 4945 * multiplier
    @Value("${contactfilter.multiplier}")
    private int multiplier;
    private final ContactRepository contactRepository;
    private final MongoContactRepository mongoContactRepository;

    @Autowired
    public ContactfilterApplication(ContactRepository contactRepository, MongoContactRepository mongoContactRepository) {
        this.contactRepository = contactRepository;
        this.mongoContactRepository = mongoContactRepository;
    }

    public static void main(String[] args) {

		SpringApplication.run(ContactfilterApplication.class, args);

	}

    @Override
    public void run(String... args) throws Exception {
        if (contactRepository.count() == 0) {
            initializeContactRepositoryData(multiplier);
        }

        if (mongoContactRepository.count().block() == 0) {
            initializeMongoContactRepositoryData(multiplier);
        }

    }

    private void initializeMongoContactRepositoryData(int repeatCount) throws IOException {
        for (int i = 0; i < repeatCount; i++) {
            System.out.println("MongoContact repository create started");

            List<String> names = getCustomizeNames();
            int size = names.size();

            names.parallelStream().forEach(s -> {
                mongoContactRepository.save(new MongoContact(s, "testEmail", "testPhone")).block();
                System.out.println("MongoContactRepository initialize: " + mongoContactRepository.count().block() + " from " + size * repeatCount);
            });
        }
    }

    private void initializeContactRepositoryData(int repeatCount) throws IOException {
        for (int i = 0; i < repeatCount; i++) {
            System.out.println("Contact repository create started");

            List<String> names = getCustomizeNames();
            int size = names.size();

            names.parallelStream().forEach(s -> {
                contactRepository.save(new Contact(s, "testEmail", "testPhone"));
                System.out.println("ContactRepository initialize: " + contactRepository.count() + " from " + size * repeatCount);
            });
        }
    }

    private List<String> getCustomizeNames() throws IOException {
        return getAllNamesFromFile().parallelStream().map(s -> s + "#" + getRandomInt(1, 10000)).collect(Collectors.toList());
    }


    private List<String> getAllNamesFromFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("static/first-names.txt")).getFile());

        return Files.readAllLines(file.toPath());
    }

    private int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }



}
