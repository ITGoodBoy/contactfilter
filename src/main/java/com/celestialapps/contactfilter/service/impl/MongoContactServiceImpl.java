package com.celestialapps.contactfilter.service.impl;

import com.celestialapps.contactfilter.model.MongoContact;
import com.celestialapps.contactfilter.repository.MongoContactRepository;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.MongoContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoContactServiceImpl implements MongoContactService {

    private MongoContactRepository mongoContactRepository;

    @Autowired
    public MongoContactServiceImpl(MongoContactRepository mongoContactRepository) {
        this.mongoContactRepository = mongoContactRepository;
    }

    @Override
    public Flux<MongoContact> getContactsWithJavaRegularFilter(String javaRegularFilter) {
        return mongoContactRepository.findAll().filter(mongoContact -> !mongoContact.getName().matches(javaRegularFilter));
    }


    @Override
    public Flux<MongoContact> addContacts(List<ContactRequest> contactRequests) {
        List<MongoContact> mongoContacts = contactRequests.parallelStream().map(MongoContact::new).collect(Collectors.toList());
        return mongoContactRepository.saveAll(mongoContacts);
    }



}
