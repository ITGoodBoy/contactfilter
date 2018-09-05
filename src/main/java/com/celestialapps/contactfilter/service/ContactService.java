package com.celestialapps.contactfilter.service;

import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.request.ContactRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {


    Page<Contact> getContactsWithJavaRegularFilter(String javaRegularFilter, Pageable pageable);
    List<Contact> getContactsWithJavaRegularFilter(String javaRegularFilter);
    List<Contact> addContacts(List<ContactRequest> contactRequests);


    List<Contact> getDisorderedContactsWithJavaRegularFilter(String javaRegularFilter);
    Page<Contact> getDisorderedContactsWithJavaRegularFilter(String javaRegularFilter, Pageable pageable);
}
