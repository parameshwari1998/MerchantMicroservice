package com.project.merchantmicroservice.service.impl;


import com.project.merchantmicroservice.dto.MerchantDetailsForProductPageDto;
import com.project.merchantmicroservice.dto.SoldProductsDto;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.repository.ProductDetailsRepository;
import com.project.merchantmicroservice.service.MerchantService;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Autowired
    MerchantService merchantService;

    @Override
    public void updateProductDetails(ProductDetails productDetails) {
        productDetails.setTotalProductsSold(0);
        productDetailsRepository.save(productDetails);
    }

    @Override
    @Transactional
    public void updateProduct(ProductDetails productDetails) {
        productDetailsRepository.update(productDetails.getProductId(),productDetails.getMerchantId(),productDetails.getProductQuantity(),productDetails.getProductPrice());
    //    merchantService.computeRating();
    }


    @Transactional
    @Override
    public void removeProduct(String productId, String merchantId) {
        productDetailsRepository.removeProduct(productId,merchantId);
     //   merchantService.computeRating();
    }

    @Override
    public ProductDetails getProductDetails(String productId, String merchantId) {
        return productDetailsRepository.getProductDetails(productId,merchantId);
    }

    @Override
    public List<ProductDetails> getProductOfMerchant(String merchantId) {
        return productDetailsRepository.findByMerchantId(merchantId);
    }


    @Override
    public List<ProductDetails> merchantsForProduct(String productId) {
        return productDetailsRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public boolean updateSoldProducts(List<SoldProductsDto> soldProductsDto ){
        for (SoldProductsDto soldProductDto:soldProductsDto) {
            if(soldProductDto.getQuantityBrought()>getProductDetails(soldProductDto.getProductId(),soldProductDto.getMerchantId()).getProductQuantity()){
                return false;
            }
            else {
                productDetailsRepository.updateProductsSold(soldProductDto.getProductId(),soldProductDto.getMerchantId(),soldProductDto.getQuantityBrought());
              //  merchantService.computeRating();
            }
        }
        return true;
    }


    @Override
    public List<ProductDetails> getproductsOrderByProductQuantityDesc(String merchantId) {
        return productDetailsRepository.findByMerchantIdOrderByProductQuantityDesc(merchantId);
    }

    @Override
    public List<ProductDetails> getproductsOrderByProductPriceDesc(String merchantId) {
        return productDetailsRepository.findByMerchantIdOrderByProductPriceDesc(merchantId);
    }

//    @Override
//    public List<MerchantDetailsForProductPageDto> getMerchantsForProduct(String productId) throws Exception {
//        return productDetailsRepository.findMerchantsForProduct(productId);
//    }


    @Override
    public List<MerchantDetailsForProductPageDto> getMerchantsForProduct(String productId) {
        List<ProductDetails> productDetailList = productDetailsRepository.findByProductId(productId);
        return productDetailList.stream().map(productDetails -> createMerchantDetailsForProductPageDto(productDetails)).collect(Collectors.toList());

    }

    private MerchantDetailsForProductPageDto createMerchantDetailsForProductPageDto(ProductDetails productDetails) {
        return MerchantDetailsForProductPageDto.builder()
                .merchantId(productDetails.getMerchantId())
                .merchantName(merchantService.getMerchantDetails(productDetails.getMerchantId()).getName())                     //productDetails.getMerchant().getName()
                .productPrice(productDetails.getProductPrice())
                .build();
    }
}
