package com.viewb.contactlist.repository.contact;

import com.viewb.contactlist.domain.model.Contact;
import com.viewb.contactlist.domain.model.Contact_;
import com.viewb.contactlist.repository.filter.ContactFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ContactRepositoryQueryImpl implements ContactRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Contact> filterContact(ContactFilter contactFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);

        Predicate[] predicates = restrictions(contactFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Contact> query = manager.createQuery(criteria);

        return query.getResultList();
    }


    /**
     * Este método de paginação não foi utilizado, porque ele quebra o sorting do tuebo table...
     * */
/*    @Override
    public List<Contact> filterContact(ContactFilter contactFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);

        Predicate[] predicates = restrictions(contactFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Contact> query = manager.createQuery(criteria);
        paginationConfig(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(contactFilter));
        return query.getResultList();
      }*/

    private Predicate[] restrictions(ContactFilter contactFilter, CriteriaBuilder builder, Root<Contact> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(contactFilter.getName())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Contact_.name)), "%" + contactFilter.getName().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(contactFilter.getLastName())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Contact_.lastName)), "%" + contactFilter.getLastName().toLowerCase() + "%"));
        }

        if (!StringUtils.isEmpty(contactFilter.getTelephone())) {
            predicates.add(builder.equal(
                    builder.lower(root.get(Contact_.telephone)), contactFilter.getTelephone().toLowerCase()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void paginationConfig(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(ContactFilter contactFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Contact> root = criteria.from(Contact.class);

        Predicate[] predicates = restrictions(contactFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
