package org.selmag.catalogue.repositories;

import org.selmag.catalogue.entity.Product ;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Integer id);

    void delete(Integer id);

    void udpate(Integer id, String title, String description);
}
