package com.celestialapps.contactfilter.controller;

import com.celestialapps.contactfilter.model.MongoContact;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.MongoContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.util.List;

@RestController
@RequestMapping("/hello/mongo/contacts")
public class MongoContactController {


    private MongoContactService mongoContactService;

    @Autowired
    public MongoContactController(MongoContactService mongoContactService) {
        this.mongoContactService = mongoContactService;
    }


    @GetMapping("/")
    public ResponseEntity<ParallelFlux<MongoContact>> findContactsByJavaFilter(@RequestParam("nameFilter") String nameFilter) {
        return ResponseEntity.ok(mongoContactService.getContactsWithJavaRegularFilter(nameFilter));
    }


    @PutMapping("/")
    public ResponseEntity<Flux<MongoContact>> addContacts(@RequestBody List<ContactRequest> contactRequests) {
        return ResponseEntity.ok(mongoContactService.addContacts(contactRequests));
    }
}
