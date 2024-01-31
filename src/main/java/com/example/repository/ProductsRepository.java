package com.example.repository;

import com.example.model.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductsModel, Integer> {
}
