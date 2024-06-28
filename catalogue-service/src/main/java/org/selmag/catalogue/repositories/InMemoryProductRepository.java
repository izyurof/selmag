package org.selmag.catalogue.repositories;

import org.springframework.stereotype.Repository;

import org.selmag.catalogue.entity.Product;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new LinkedList<>();

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.stream().max(Comparator.comparing(Product::getId)).map(Product::getId).orElse(0) + 1);
        this.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return this.products.stream().filter(product -> Objects.equals(id, product.getId())).findFirst();
    }

    @Override
    public void delete(Integer id) {
        this.products.removeIf(product -> Objects.equals(id, product.getId()));
    }

    @Override
    public void udpate(Integer id, String title, String description) {
        findById(id).ifPresentOrElse(product -> {
            product.setTitle(title);
            product.setDescription(description);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

}
