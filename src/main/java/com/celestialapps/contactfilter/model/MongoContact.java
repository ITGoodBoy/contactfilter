package com.celestialapps.contactfilter.model;

import com.celestialapps.contactfilter.request.ContactRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "mongoContacts")
public class MongoContact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;


    public MongoContact(ContactRequest contactRequest) {
        this.name = contactRequest.getName();
        this.email = contactRequest.getEmail();
        this.phoneNumber = contactRequest.getPhoneNumber();
    }
}
