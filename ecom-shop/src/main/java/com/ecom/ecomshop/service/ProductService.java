package com.ecom.ecomshop.service;

import com.ecom.ecomshop.model.Product;
import com.ecom.ecomshop.payload.ProductDTO;
import com.ecom.ecomshop.payload.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);
}
