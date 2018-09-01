package com.celestialapps.contactfilter.controller;

import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello/contacts")
public class ContactController {


    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Contact>> findContactsByJavaFilter(@RequestParam("nameFilter") String nameFilter) {
        return ResponseEntity.ok(contactService.getContactsWithJavaRegularFilter(nameFilter));
    }


    @PutMapping("/")
    public ResponseEntity<List<Contact>> addContacts(@RequestBody List<ContactRequest> contactRequests) {
        return ResponseEntity.ok(contactService.addContacts(contactRequests));
    }
}
