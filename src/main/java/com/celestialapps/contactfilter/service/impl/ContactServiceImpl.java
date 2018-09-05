package com.celestialapps.contactfilter.service.impl;

import com.celestialapps.contactfilter.Utils.RegexUtils;
import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.repository.ContactRepository;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.ContactService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Page<Contact> getContactsWithJavaRegularFilter(String javaRegularFilter, Pageable pageable) {
       List<String> allContactNames = contactRepository.findAllNames();

       allContactNames = RegexUtils.getNotRegexList(allContactNames, javaRegularFilter);
       allContactNames.sort(String::compareTo);

       List<List<String>> dividedContactNames = Lists.partition(allContactNames, 20000);

       List<Contact> contacts = new ArrayList<>();

     //  int page = pageable.getPageNumber();

       for (List<String> stringList : dividedContactNames) {
           List<Contact> contactList = contactRepository
                   .findAllByNameIn(stringList);

           contacts.addAll(contactList);
       }

       contacts.sort(Comparator.comparing(Contact::getName));

       int offset = pageable.getPageNumber() * pageable.getPageSize();

       return new PageImpl<>(contacts.subList(offset, offset + pageable.getPageSize()), pageable, contacts.size());
    }

    @Override
    public List<Contact> getContactsWithJavaRegularFilter(String regEx) {
        List<String> contactNames = contactRepository.findAllNames();

        contactNames = RegexUtils.getNotRegexList(contactNames, regEx);

        //PostgreSql parameters count limit = 32767
        List<List<String>> dividedContactNames = Lists.partition(contactNames, 32767);

        return dividedContactNames
                .stream()
                .map(strings -> contactRepository.findAllByNameIn(strings))
                .flatMap((Function<List<Contact>, Stream<Contact>>) Collection::stream)
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> addContacts(List<ContactRequest> contactRequests) {
        List<Contact> contacts = contactRequests.parallelStream().map(Contact::new).collect(Collectors.toList());
        return contactRepository.saveAll(contacts);
    }

    @Override
    public List<Contact> getDisorderedContactsWithJavaRegularFilter(String javaRegularFilter) {
        List<String> allContactNames = contactRepository.findAllNames();

        allContactNames = RegexUtils.getNotRegexList(allContactNames, javaRegularFilter);
        allContactNames.sort(String::compareTo);

        AtomicInteger atomicInteger = new AtomicInteger();

        return allContactNames
                .stream()
                .map(s -> new Contact(atomicInteger.getAndIncrement(), s))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Contact> getDisorderedContactsWithJavaRegularFilter(String javaRegularFilter, Pageable pageable) {
        List<String> allContactNames = contactRepository.findAllNames();

        allContactNames = RegexUtils.getNotRegexList(allContactNames, javaRegularFilter);
        allContactNames.sort(String::compareTo);

        AtomicInteger atomicInteger = new AtomicInteger();

        List<Contact> contacts = allContactNames
                .stream()
                .map(s -> new Contact(atomicInteger.getAndIncrement(), s))
                .collect(Collectors.toList());

        int offset = pageable.getPageNumber() * pageable.getPageSize();

        return new PageImpl<>(contacts.subList(offset, offset + pageable.getPageSize()), pageable, contacts.size());
    }


}
