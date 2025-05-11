package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDTO;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false, name="categoryId") Long categoryId){

        List<Product> products;

        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }else{
            products = productRepository.findAll();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);

        if(product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping()
    public Product createProduct(@RequestBody ProductDTO request){
        var product = productMapper.toEntity(request);
        productRepository.save(product);
        var productDto = productMapper.toDto(product);
        return product;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return ResponseEntity.ok(productDto);
    }

}
