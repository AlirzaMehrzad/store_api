package com.alirezamehrzad.store.repositories;

import com.alirezamehrzad.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}