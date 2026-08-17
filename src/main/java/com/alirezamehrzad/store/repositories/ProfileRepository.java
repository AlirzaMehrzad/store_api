package com.alirezamehrzad.store.repositories;

import com.alirezamehrzad.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}