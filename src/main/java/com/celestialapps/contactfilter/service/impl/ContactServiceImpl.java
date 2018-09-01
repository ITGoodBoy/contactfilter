package com.celestialapps.contactfilter.service.impl;

import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.repository.ContactRepository;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getContactsWithJavaRegularFilter(String javaRegularFilter) {
        List<Contact> contacts = contactRepository.findAll();

        return contacts.parallelStream()
                .filter(contact -> !contact.getName().matches(javaRegularFilter))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> addContacts(List<ContactRequest> contactRequests) {
        List<Contact> contacts = contactRequests.parallelStream().map(Contact::new).collect(Collectors.toList());
        return contactRepository.saveAll(contacts);
    }


}
