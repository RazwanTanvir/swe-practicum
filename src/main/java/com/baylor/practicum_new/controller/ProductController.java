package com.baylor.practicum_new.controller;

import com.baylor.practicum_new.dto.ProductDTO;
import com.baylor.practicum_new.dto.UserProductsDTO;
import com.baylor.practicum_new.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> productDetails) {
        Long userId = Long.parseLong(productDetails.get("userId").toString());
        String productName = productDetails.get("productName").toString();
        String description = productDetails.get("description").toString();

        ProductDTO product = productService.createProduct(userId, productName, description);
        return new ResponseEntity<>(Collections.singletonMap("productId", product.getProductId()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/:{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDTO> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserProductsDTO>> getProductsGroupedByUsers() {
        return new ResponseEntity<>(productService.getAllUsersWithProducts(), HttpStatus.OK);
    }
}
