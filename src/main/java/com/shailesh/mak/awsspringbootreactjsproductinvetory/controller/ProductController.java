package com.shailesh.mak.awsspringbootreactjsproductinvetory.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.model.Product;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.model.ProductRequest;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "product",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "description", required = false) String description,
                                              @RequestParam(value = "company", required = false) String company,
                                              @RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "price", required = false) String price,
                                              @RequestParam(value = "imageUrl", required = false) String imageUrl,
                                              @RequestParam(value = "file", required = false) MultipartFile file
                                              ) throws IOException {
        Product _product = null;
        try {
            _product = productService.addProduct(new ProductRequest(
                    name,
                    description,
                    company,
                    category,
                    price,
                    imageUrl,
                    file
            ));
        } catch (IOException e) {
            throw e;
        }

        if (_product != null) {
            return new ResponseEntity<Product>(_product, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("product/{id}/image/download")
    public byte[] downloadProductImage(@PathVariable("id") Integer id) {
        return productService.downloadProductImage(id);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>>  getProducts() {
        List<Product> _products = productService.getProducts();
        if(!_products.isEmpty()) {
            return new ResponseEntity<>(_products, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getUser(@PathVariable final Integer id) {
        Optional<Product>_product = productService.findById(id);
        if(_product.isPresent()) {
            return new ResponseEntity<Product>(_product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateUser(@PathVariable final Integer id, @RequestBody final Product product) {
        Optional<Product>_product = productService.updateUser(id, product);
        if(_product.isPresent()) {
            return new ResponseEntity<Product>(_product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable final Integer id) {
        Optional<Product>_product = productService.deleteProduct(id);
        if(_product.isPresent()) {
            return new ResponseEntity<Product>(_product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
