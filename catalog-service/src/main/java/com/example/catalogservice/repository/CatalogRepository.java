package com.example.catalogservice.repository;

import com.example.catalogservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}
