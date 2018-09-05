package com.celestialapps.contactfilter.controller;

import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.request.ContactRequest;
import com.celestialapps.contactfilter.service.ContactService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("/hello/contacts")
public class ContactController {


    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/by-page")
    @ApiOperation(value = "Постраничный поиск Контактов, сортировка по name")
    public ResponseEntity<Page<Contact>> findPageContactsByJavaFilter(@RequestParam("nameFilter") String nameFilter, Pageable pageable) {

        try {
            Pattern.compile(nameFilter);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(contactService.getContactsWithJavaRegularFilter(nameFilter, pageable));
    }

    @GetMapping("/by-page-list")
    @ApiOperation(value = "return findPageContactsByJavaFilter list")
    public ResponseEntity<List<Contact>> findPageContactsByJavaFilterList(@RequestParam("nameFilter") String nameFilter, Pageable pageable) {
        return ResponseEntity.ok(findPageContactsByJavaFilter(nameFilter, pageable).getBody().getContent());
    }


    @GetMapping("/")
    @ApiOperation(value = "Поиск по фильтру по всей базе данных")
    public ResponseEntity<List<Contact>> findContactsByJavaFilter(@RequestParam("nameFilter") String nameFilter) {

        try {
            Pattern.compile(nameFilter);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(contactService.getContactsWithJavaRegularFilter(nameFilter));
    }

    @GetMapping("/disordered")
    @ApiOperation(value = "Возвращает только имена, не имеет актуальных данных других полей с бд")
    public ResponseEntity<List<Contact>> findDisorderedContactsWithJavaRegularFilter(@RequestParam("nameFilter") String nameFilter) {

        try {
            Pattern.compile(nameFilter);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(contactService.getDisorderedContactsWithJavaRegularFilter(nameFilter));
    }

    @GetMapping("/by-page-disordered")
    @ApiOperation(value = "Постранично возвращает только имена, не имеет актуальных данных других полей с бд")
    public ResponseEntity<Page<Contact>> findDisorderedContactsWithJavaRegularFilter(@RequestParam("nameFilter") String nameFilter,
                                                                                     Pageable pageable) {

        try {
            Pattern.compile(nameFilter);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(contactService.getDisorderedContactsWithJavaRegularFilter(nameFilter, pageable));
    }


    @PutMapping("/")
    @ApiOperation(value = "Добавляет список контактов в бд")
    public ResponseEntity<List<Contact>> addContacts(@RequestBody List<ContactRequest> contactRequests) {
        return ResponseEntity.ok(contactService.addContacts(contactRequests));
    }
}
