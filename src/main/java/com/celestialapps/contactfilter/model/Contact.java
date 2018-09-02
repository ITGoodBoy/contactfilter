package com.celestialapps.contactfilter.model;

import com.celestialapps.contactfilter.request.ContactRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Contact {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String phoneNumber;

    public Contact(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Contact(ContactRequest contactRequest) {
        this.name = contactRequest.getName();
        this.email = contactRequest.getEmail();
        this.phoneNumber = contactRequest.getPhoneNumber();
    }


}
