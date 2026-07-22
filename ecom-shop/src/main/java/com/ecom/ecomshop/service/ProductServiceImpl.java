package com.ecom.ecomshop.service;

import com.ecom.ecomshop.exceptions.ResourceNotFoundException;
import com.ecom.ecomshop.model.Category;
import com.ecom.ecomshop.model.Product;
import com.ecom.ecomshop.payload.ProductDTO;
import com.ecom.ecomshop.payload.ProductResponse;
import com.ecom.ecomshop.repositories.CategoryRepository;
import com.ecom.ecomshop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setImage("default png");
        product.setCategory(category);
        double specialPrice = product.getPrice() -
                ((product.getDiscount() * 0.01) * product.getPrice());

        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));


        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        productFromDB.setProductName(product.getProductName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setPrice(product.getPrice());
        productFromDB.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDB);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId ));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

}
