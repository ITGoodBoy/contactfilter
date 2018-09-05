package com.celestialapps.contactfilter.repository;

import com.celestialapps.contactfilter.model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {


    @Override
    List<Contact> findAll();

    @Query(nativeQuery = true, value = "SELECT *, (regexp_matches(name, :regex)) from Contact")
    List<Contact> findByNameRegex(@Param("regex") String regex);

    @Query(nativeQuery = true, value = "SELECT *, (regexp_matches(name, :regex)) from Contact")
    List<Contact> findByNameRegex(@Param("regex") String regex, Pageable pageable);

    List<Contact> findAllByNameIn(List<String> names);


    @Query("select c.name from Contact c")
    List<String> findAllNames();

    @Override
    <S extends Contact> List<S> saveAll(Iterable<S> iterable);
}
