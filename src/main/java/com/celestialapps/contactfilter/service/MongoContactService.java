package com.celestialapps.contactfilter.service;

import com.celestialapps.contactfilter.model.MongoContact;
import com.celestialapps.contactfilter.request.ContactRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.util.List;

public interface MongoContactService {


    ParallelFlux<MongoContact> getContactsWithJavaRegularFilter(String javaRegularFilter);

    Flux<MongoContact> addContacts(List<ContactRequest> contactRequests);
}
