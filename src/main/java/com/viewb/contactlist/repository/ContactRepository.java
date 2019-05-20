package com.viewb.contactlist.repository;

import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.repository.contact.ContactRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, ContactRepositoryQuery {
}
