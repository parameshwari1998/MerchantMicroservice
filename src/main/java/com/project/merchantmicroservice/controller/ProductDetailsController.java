package com.project.merchantmicroservice.controller;


import com.project.merchantmicroservice.dto.*;
import com.project.merchantmicroservice.entity.Merchant;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/productdetails")
public class ProductDetailsController {

    @Autowired
    ProductDetailsService productDetailsService;

    @PostMapping("/updateProduct")
    public ResponseDto<ProductDetails> updateProductDetails(@RequestBody ProductDetailsDto productDetailsDto){
        ProductDetails productDetails=new ProductDetails();
        Merchant merchant = new Merchant();
        merchant.setMerchantId(productDetailsDto.getMerchantId());
        BeanUtils.copyProperties(productDetailsDto,productDetails);
        productDetails.setMerchant(merchant);
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try{
            productDetailsService.updateProductDetails(productDetails);
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
            exception.printStackTrace();
        }
        return responseDto;
    }

    @PostMapping("/updateQuantity")
    public ResponseDto<ProductDetails> updateProductQuantity(@RequestBody ProductDetailsDto productDetailsDto){
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            productDetailsService.updateProductQuantity(productDetailsDto.getProductId(),productDetailsDto.getMerchantId(),productDetailsDto.getProductQuantity());
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;
    }

    @PostMapping("/updatePrice")
    public ResponseDto<ProductDetails> updateProductPrice(@RequestBody ProductDetailsDto productDetailsDto){
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            productDetailsService.updateProductPrice(productDetailsDto.getProductId(),productDetailsDto.getMerchantId(),productDetailsDto.getProductPrice());
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;

    }

    @GetMapping("/product/")
    public ResponseDto<ProductDetailsOfMerchantDto> getProductDetails(@RequestBody ProductDetailsDto productDetailsDto){
        ResponseDto<ProductDetailsOfMerchantDto> responseDto=new ResponseDto<>();
        try {
            //by Swarnim    (send data to product microservice)
            ProductDetails productDetails=productDetailsService.getProductDetails(productDetailsDto.getProductId(),productDetailsDto.getMerchantId());
            final String uri="http://10.177.68.26:8080/product/getProductDetailsOfMerchant";
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            productDetails.setMerchant(null);
            HttpEntity<ProductDetails> entityReq=new HttpEntity<>(productDetails,headers);
            RestTemplate restTemplate=new RestTemplate();
            ProductDetailsOfMerchantDto result=restTemplate.postForObject(uri,entityReq,ProductDetailsOfMerchantDto.class);
            result.setProductDetails(productDetails);
            responseDto.setData(result);            //ends
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't load details of the product");
            exception.printStackTrace();
        }
        return responseDto;
    }

    @GetMapping("/merchantProduct/{id}")
    public ResponseDto<List<ProductDto>> getProductsSoldByMerchant(@PathVariable("id") String merchantId){
        ResponseDto<List<ProductDto>> responseDto=new ResponseDto<>();
        try {
            List<ProductDetails> productDetails= productDetailsService.getProductOfMerchant(merchantId);
            final String uri="http://10.177.68.26:8080/product/listOfProductsByMerchant";
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<List<ProductDetails>> entityReq=new HttpEntity<>(productDetails,headers);
            RestTemplate restTemplate=new RestTemplate();
            List<ProductDto> result=restTemplate.postForObject(uri,entityReq,List.class);
            responseDto.setData(result);
            responseDto.setSuccess(true);
        }catch (Exception exception){
            exception.printStackTrace();
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't load details");
        }
        return responseDto;
    }

    @PostMapping("/stockUpdate")
    public boolean updateStock(@RequestBody List<SoldProductsDto> soldProductsDtos){
        return productDetailsService.updateSoldProducts(soldProductsDtos);
    }

    @PostMapping("/merchantProductsList")
    public ResponseEntity<List<MerchantDetailsForProductPageDto>> getListOfMerchantsForProduct(@RequestBody String productId){
        List<MerchantDetailsForProductPageDto> merchantList= null;
        try {
            merchantList = productDetailsService.getMerchantsForProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<MerchantDetailsForProductPageDto>>(merchantList,HttpStatus.CREATED);
    }


}