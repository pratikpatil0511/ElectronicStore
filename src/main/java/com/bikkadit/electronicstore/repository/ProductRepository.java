package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    //search
    Page<Product> findByTitleContaining(Pageable pageable,String keywords);

    //returns all products with live value 'True'
   Page<Product> findByLiveTrue(Pageable pageable);
}
