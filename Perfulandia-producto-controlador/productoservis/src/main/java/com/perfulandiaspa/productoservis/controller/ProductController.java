package com.perfulandiaspa.productoservis.controller;

import com.perfulandiaspa.productoservis.model.Product;
import com.perfulandiaspa.productoservis.service.ProductServiceImpl;
import jakarta.validation.Valid; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.stream.Collectors;




import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

 
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = productService.getAllProducts().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("productos")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);

        return product
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("productos")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {    
        product.setId(null);
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (productService.deleteProduct(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }
}