package org.selmag.catalogue.services;

import org.selmag.catalogue.entity.Product;
import lombok.RequiredArgsConstructor;
import org.selmag.catalogue.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description) {
        return this.productRepository.save(new Product(null, title, description));
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(Integer id, String title, String description) {
        this.productRepository.udpate(id, title, description);
    }

    @Override
    public void delete(Integer id) {
        this.productRepository.delete(id);
    }

}
