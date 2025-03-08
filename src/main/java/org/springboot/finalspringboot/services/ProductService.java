package org.springboot.finalspringboot.services;

import org.springboot.finalspringboot.model.entities.Product;
import org.springboot.finalspringboot.model.repotories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> searchProduct(String category) {
        return productRepository.findByNameContaining(category);
    }
}
