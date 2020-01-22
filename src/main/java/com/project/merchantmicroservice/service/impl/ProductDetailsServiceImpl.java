package com.project.merchantmicroservice.service.impl;


import com.project.merchantmicroservice.dto.MerchantDetailsForProductPageDto;
import com.project.merchantmicroservice.dto.ProductDetailsDto;
import com.project.merchantmicroservice.dto.ProductDto;
import com.project.merchantmicroservice.dto.SoldProductsDto;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.repository.ProductDetailsRepository;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Override
    public void updateProductDetails(ProductDetails productDetails) {
        productDetailsRepository.save(productDetails);
    }

    @Override
    public void updateProductQuantity(String productId, String merchantId, Integer productQuantity) {
        productDetailsRepository.updateProductQuantity(productId,merchantId,productQuantity);
    }

    @Override
    public void updateProductPrice(String productId, String merchantId, double productPrice) {
        productDetailsRepository.updateProductPrice(productId,merchantId,productPrice);
    }

    @Override
    public ProductDetails getProductDetails(String productId, String merchantId) {
        return productDetailsRepository.getProductDetails(productId,merchantId);
    }

    @Override
    public List<ProductDetails> getProductOfMerchant(String merchantId) {
        return productDetailsRepository.findByMerchant(merchantId);
    }


    @Override
    @Transactional
    public boolean updateSoldProducts(List<SoldProductsDto> soldProductsDto){
        for (SoldProductsDto soldProductDto:soldProductsDto) {
            if(soldProductDto.getQuantityBrought()>getProductDetails(soldProductDto.getProductId(),soldProductDto.getMerchantId()).getProductQuantity()){
                return false;
            }
            else {
                productDetailsRepository.updateProductsSold(soldProductDto.getProductId(),soldProductDto.getMerchantId(),soldProductDto.getQuantityBrought());
            }
        }
        return true;
    }


    @Override
    public List<MerchantDetailsForProductPageDto> getMerchantsForProduct(String productId) {
        List<ProductDetails> productDetailList = productDetailsRepository.findByProductId(productId);
        return productDetailList.stream().map(productDetails -> createMerchantDetailsForProductPageDto(productDetails)).collect(Collectors.toList());

    }

    private MerchantDetailsForProductPageDto createMerchantDetailsForProductPageDto(ProductDetails productDetails) {
        return MerchantDetailsForProductPageDto.builder()
                .merchantId(productDetails.getMerchant().getMerchantId())
                .merchantName(productDetails.getMerchant().getName())
                .productPrice(productDetails.getProductPrice())
                .build();
    }
}
