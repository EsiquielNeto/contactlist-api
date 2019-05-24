package com.viewb.contactlist.domain.service;

import com.viewb.contactlist.application.exception.GenericException;
import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.repository.ContactRepository;
import com.viewb.contactlist.repository.filter.ContactFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    private Class<Contact> getEntityClass(){
        return Contact.class;
    }

    private String getModelName() {
        return this.getEntityClass().getSimpleName().substring(0, 1).toLowerCase() + this.getEntityClass().getSimpleName().substring(1);
    }

    public List<Contact> filterContact(ContactFilter contactFilter) {
        return contactRepository.filterContact(contactFilter);
    }

    public Contact findById(Long id) {
        existsById(id);
        return contactRepository.getOne(id);
    }

    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact update(Contact contact, Long id) {
        Contact contactSave = findById(id);
        BeanUtils.copyProperties(contact, contactSave, "id");

        return contactRepository.save(contactSave);
    }

    public void delete(Long id) {
        existsById(id);
        contactRepository.deleteById(id);
    }

    private void existsById(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new GenericException(getModelName() + ".notFound");
        }
    }
}
