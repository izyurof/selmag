package org.selmag.manager.services;

import org.selmag.manager.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();

    Product createProduct(String title, String description);

    Optional<Product> findById(Integer id);

    void update(Integer id, String title, String description);

    void delete(Integer id);
}
