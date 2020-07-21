package com.shailesh.mak.awsspringbootreactjsproductinvetory.repository;

import com.shailesh.mak.awsspringbootreactjsproductinvetory.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
