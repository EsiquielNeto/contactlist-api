package com.viewb.contactlist.repository.contact;

import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.repository.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactRepositoryQuery {
    List<Contact> filterContact(ContactFilter contactFilter);
}
