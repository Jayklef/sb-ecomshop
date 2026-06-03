package com.ecom.ecomshop.repositories;

import com.ecom.ecomshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
