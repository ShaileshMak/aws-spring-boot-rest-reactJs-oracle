package com.shailesh.mak.awsspringbootreactjsproductinvetory.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.model.Product;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.model.ProductRequest;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.repository.ProductRepository;
import com.shailesh.mak.awsspringbootreactjsproductinvetory.utils.BucketName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private AmazonS3 s3;

    @Autowired
    public ProductService(ProductRepository productRepository, AmazonS3 s3) {
        this.productRepository = productRepository;
        this.s3 = s3;
    }

    public Product addProduct(final ProductRequest productRequest) throws IOException {
        Product _product = generateProductFromRequest(productRequest);
        //save product to db first to get the ID for storing on AWS S3
        Product _updatedProduct =  productRepository.save(_product);
        if(_updatedProduct.getImageUrl() != null) {
            try {
                storeProductImageToS3(productRequest, _updatedProduct.getId());
            } catch (IOException e) {
                //delete if not able to store image on S3
                deleteProduct(_updatedProduct.getId());
                throw e;
            }
        }
        return _updatedProduct;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> findById(final Integer id) {
        return productRepository.findById(id);
    }

    public Optional<Product> updateUser(Integer id, Product product) {
       Optional<Product> _product = productRepository.findById(id);
       Product _prod = _product.get();
       if (_prod != null) {
           _prod.setName(product.getName());
           _prod.setDescription(product.getDescription());
           _prod.setCompany(product.getCompany());
           _prod.setCategory(product.getCategory());
           _prod.setPrice(product.getPrice());
           _prod.setImageUrl(product.getImageUrl().get());
           productRepository.save(_prod);
       }
       return _product;
    }

    public Optional<Product> deleteProduct(Integer id) {
        Optional<Product> _product = productRepository.findById(id);
        Product _prod = _product.get();
        if (_prod != null) {
            productRepository.delete(_prod);
        }
        return _product;
    }

    public void saveProductImage(String path,
                                 String name,
                                 Optional<Map<String, String>> optionalMetadata,
                                 InputStream file) {
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, name, file, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    public byte[] downloadProductImage(Integer id) {
        Optional<Product> _product = productRepository.findById(id);
        Product _prod = _product.get();
        if (_prod != null) {
            String path = String.format("%s/%s",
                    BucketName.BUCKET_NAME.getBucketName(),
                    _prod.getId());

            return _prod.getImageUrl()
                    .map(key -> downloadImage(path, key))
                    .orElse(new byte[0]);
        }
        return new byte[0];
    }

    public byte[] downloadImage(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }


    private Product generateProductFromRequest(ProductRequest productRequest) {
        return new Product(
                        productRequest.getName(),
                        productRequest.getDescription(),
                        productRequest.getCompany(),
                        productRequest.getCategory(),
                        productRequest.getPrice(),
                        String.format(productRequest.getImageUrl())
                );
    }

    private void storeProductImageToS3(ProductRequest productRequest, Integer id) throws IOException {
        String fileUri = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(), id);
        saveProductImage(
                fileUri,
                productRequest.getImageUrl(),
                extractMetadata(productRequest.getImage()),
                productRequest.getImage().getInputStream()
        );
    }

    private Optional<Map<String, String>> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return Optional.of(metadata);
    }
}
