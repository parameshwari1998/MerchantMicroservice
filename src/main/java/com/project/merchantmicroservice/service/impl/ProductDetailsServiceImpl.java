package com.project.merchantmicroservice.service.impl;


import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.repository.ProductDetailsRepository;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Override
    public ProductDetails updateProductDetails(ProductDetails productDetails) {
        return productDetailsRepository.save(productDetails);
    }

    @Override
    public ProductDetails updateProductQuantity(String productId, String merchantId, Integer quantity) {
        return productDetailsRepository.updateProductQuantity(productId,merchantId,quantity);
    }

    @Override
    public ProductDetails updateProductPrice(String productId, String merchantId, Integer price) {
        return productDetailsRepository.updateProductPrice(productId,merchantId,price);
    }

    @Override
    public ProductDetails getProductDetails(String productId, String merchantId) {
        return productDetailsRepository.getProductDetails(productId,merchantId);
    }
}
