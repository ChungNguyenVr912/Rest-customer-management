package com.rest_cm.repository;

import com.rest_cm.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository
        extends CrudRepository<Customer,Long>, PagingAndSortingRepository<Customer,Long> {
}
