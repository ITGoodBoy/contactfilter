package com.celestialapps.contactfilter.repository;

import com.celestialapps.contactfilter.model.MongoContact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoContactRepository extends ReactiveMongoRepository<MongoContact, String> {



}
