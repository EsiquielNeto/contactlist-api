package com.viewb.contactlist.repository.contact;

import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.repository.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepositoryQuery {
    Page<Contact> filterContact(ContactFilter contactFilter, Pageable pageable);
}
