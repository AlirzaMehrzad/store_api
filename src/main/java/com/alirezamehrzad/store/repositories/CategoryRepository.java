package com.alirezamehrzad.store.repositories;

import com.alirezamehrzad.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}