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

import javax.validation.Valid;

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
    public ResponseEntity<Page<Contact>> findAll(ContactFilter contactFilter, Pageable pageable) {
        return new ResponseEntity<>(contactService.filterContact(contactFilter, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Contact> findById(@PathVariable Long id) {
        return new ResponseEntity<>(contactService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> create(@Valid @RequestBody Contact model) {
        return new ResponseEntity<>(contactService.create(model), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @Valid @RequestBody Contact model) {
        return new ResponseEntity<>(contactService.update(model, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
