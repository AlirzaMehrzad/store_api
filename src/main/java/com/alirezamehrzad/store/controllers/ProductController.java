package com.alirezamehrzad.store.controllers;

import com.alirezamehrzad.store.dtos.ProductDto;
import com.alirezamehrzad.store.entities.Product;
import com.alirezamehrzad.store.mappers.ProductMapper;
import com.alirezamehrzad.store.repositories.CategoryRepository;
import com.alirezamehrzad.store.repositories.ProductRepository;
import com.alirezamehrzad.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false, name= "categoryId")
            Byte categoryId
    ) {
        var products = productService.getAllProducts();
        return products.stream().map(productMapper::toDto).toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product = productService.getProductById(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
            ) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);

        // Call the service so Redis knows to clear the old cache!
        var savedProduct = productService.createProduct(product);

        productDto.setId(savedProduct.getId());
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "product", key = "#id")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ) {
        // 1. Verify the category exists
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        // 2. Map DTO to Entity
        var productToUpdate = productMapper.toEntity(productDto);
        productToUpdate.setCategory(category);

        try {
            // 3. Call the service (which automatically clears Redis!)
            var updatedProduct = productService.updateProduct(id, productToUpdate);
            return ResponseEntity.ok(productMapper.toDto(updatedProduct));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            // Call the service to delete from DB and clear Redis
            productService.deleteProduct(id);

            // Standard HTTP response for a successful deletion is 204 No Content
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
