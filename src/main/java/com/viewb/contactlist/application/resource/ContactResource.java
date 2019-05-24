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

    @PostMapping
    public ResponseEntity<Contact> create(@Valid @RequestBody Contact model) {
        return new ResponseEntity<>(contactService.create(model), HttpStatus.CREATED);
    }

    @PostMapping("/photo")
    public String uploadPhoto(@RequestParam MultipartFile file) throws IOException {
        OutputStream out = new FileOutputStream(
                "/home/esiquiel/Imagens/photos/upload--" + file.getOriginalFilename());
        out.write(file.getBytes());
        out.close();
        return "ok";
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
