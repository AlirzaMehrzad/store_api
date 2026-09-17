package com.alirezamehrzad.store.services;

import com.alirezamehrzad.store.entities.Product;
import com.alirezamehrzad.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 0. EVICT ON CREATE
    @CacheEvict(value = "productCatalog", allEntries = true) // Clears the homepage cache
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 1. CACHE THE CATALOG
    @Cacheable(value = "productCatalog") // Comment this out disable caching
    public List<Product> getAllProducts() {
        System.out.println("Fetching entire catalog from Database (Heavy operation)...");
        return productRepository.findAll();
    }

    // 2. CACHE THE SINGLE PRODUCT
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 3. EVICT ON UPDATE
    // Wipes the specific product by ID, AND wipes the entire "productCatalog" cache.
    @Caching(evict = {
            @CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "productCatalog", allEntries = true)
    })
    public Product updateProduct(Long id, Product productUpdates) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Apply the new values to the existing product
        existingProduct.setName(productUpdates.getName());
        existingProduct.setDescription(productUpdates.getDescription());
        existingProduct.setPrice(productUpdates.getPrice());
        existingProduct.setCategory(productUpdates.getCategory());

        return productRepository.save(existingProduct);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "productCatalog", allEntries = true)
    })
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}