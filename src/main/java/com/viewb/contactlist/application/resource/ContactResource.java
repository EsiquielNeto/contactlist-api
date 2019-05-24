package com.viewb.contactlist.application.resource;


import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.repository.filter.ContactFilter;
import com.viewb.contactlist.domain.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("contacts")
public class ContactResource {

    private final ContactService contactService;

    @Autowired
    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> findAll(ContactFilter contactFilter) {
        return new ResponseEntity<>(contactService.filterContact(contactFilter), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Contact> findById(@PathVariable Long id) {
        return new ResponseEntity<>(contactService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Contact> create(@RequestPart(value = "file", required = false) MultipartFile file,
                                          @Valid @RequestPart(value = "contact") Contact contact) {

        return new ResponseEntity<>(contactService.create(contact, file), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id,
                                          @RequestPart(value = "file", required = false) MultipartFile file,
                                          @Valid @RequestPart Contact contact) {
        return new ResponseEntity<>(contactService.update(contact, id, file), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
