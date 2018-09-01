package com.celestialapps.contactfilter.repository;

import com.celestialapps.contactfilter.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {


    @Override
    List<Contact> findAll();


    @Override
    <S extends Contact> List<S> saveAll(Iterable<S> iterable);
}
