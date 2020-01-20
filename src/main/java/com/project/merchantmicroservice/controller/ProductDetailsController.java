package com.project.merchantmicroservice.controller;


import com.project.merchantmicroservice.dto.ProductDetailsDto;
import com.project.merchantmicroservice.dto.ResponseDto;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productdetails")
public class ProductDetailsController {

    @Autowired
    ProductDetailsService productDetailsService;

    ResponseDto<ProductDetails> updateProductDetails(ProductDetailsDto productDetailsDto){
        ProductDetails productDetails=new ProductDetails();
        BeanUtils.copyProperties(productDetailsDto,productDetails);
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try{
            responseDto.setData(productDetailsService.updateProductDetails(productDetails));
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;
    }


    ResponseDto<ProductDetails> updateProductQuantity(String productId,String merchantId,Integer quantity){
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            responseDto.setData(productDetailsService.updateProductQuantity(productId,merchantId,quantity));
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;
    }

    ResponseDto<ProductDetails> updateProductPrice(String productId,String merchantId,Integer price){
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            responseDto.setData(productDetailsService.updateProductPrice(productId,merchantId,price));
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;

    }


    ResponseDto<ProductDetails> getProductDetails(String productId, String merchantId){
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            responseDto.setData(productDetailsService.getProductDetails(productId,merchantId));
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't load details of the product");
        }
        return responseDto;

    }

}