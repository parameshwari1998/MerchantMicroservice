package com.project.merchantmicroservice.controller;


import com.project.merchantmicroservice.dto.*;
import com.project.merchantmicroservice.entity.Merchant;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.service.MerchantService;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/merchant")
public class ProductDetailsController {

    @Autowired
    ProductDetailsService productDetailsService;

    @Autowired
    MerchantService merchantService;

    @PostMapping("/productdetails/addExistingProduct")
    public ResponseDto<ProductDetails> addExistingProduct(@RequestHeader("merchantId") String merchantId,@RequestBody ProductDetailsDto productDetailsDto){
        System.out.println("Request "+productDetailsDto.getProductId());
        productDetailsDto.setMerchantId(merchantId);
        return updateProductDetails(productDetailsDto);
    }

    @PostMapping("/productdetails/add")
    public ResponseDto<ProductDetails> updateProductDetails(@RequestBody ProductDetailsDto productDetailsDto){
        System.out.println("Request "+productDetailsDto.getProductId());
        ProductDetails productDetails=new ProductDetails();
        BeanUtils.copyProperties(productDetailsDto,productDetails);
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try{
            productDetailsService.updateProductDetails(productDetails);
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;
    }

    @PostMapping("/productdetails/update")
    public ResponseDto<ProductDetails> updateProductQuantity(@RequestHeader("merchantId") String  merchantId,@RequestBody ProductDetailsDto productDetailsDto){
        ProductDetails productDetails=new ProductDetails();
        BeanUtils.copyProperties(productDetailsDto,productDetails);
        productDetails.setMerchantId(merchantId);
        ResponseDto<ProductDetails> responseDto=new ResponseDto<>();
        try {
            productDetailsService.updateProduct(productDetails);
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update the product details");
        }
        return responseDto;
    }

    @DeleteMapping("/productdetails/remove")
    public ResponseDto<String> removeProduct(@RequestHeader("merchantId") String  merchantId,@RequestBody ProductDetailsDto productDetailsDto){
        ResponseDto<String> responseDto=new ResponseDto<>();
        System.out.println(productDetailsDto.getProductId());
        productDetailsDto.setMerchantId(merchantId);
        try {
            productDetailsService.removeProduct(productDetailsDto.getProductId(),productDetailsDto.getMerchantId());
            int merchantsForProduct=(productDetailsService.merchantsForProduct(productDetailsDto.getProductId())).size();
            if(merchantsForProduct==0){
                final String uri="http://10.177.68.40:8762/spring-cloud-eureka-client-product/product/deleteProduct/"+productDetailsDto.getProductId();
                HttpHeaders headers=new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entityReq=new HttpEntity<>("",headers);
                RestTemplate restTemplate=new RestTemplate();
                String result=restTemplate.postForObject(uri,entityReq,String.class);
                System.out.println(result);
            }
            responseDto.setSuccess(true);
            responseDto.setMessage("Product removed successfully");
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't remove product");
        }
        return responseDto;
    }

    @GetMapping("/productdetails/product/")
    public ResponseDto<ProductDetailsOfMerchantDto> getProductDetails(@RequestHeader("merchantId") String  merchantId,@RequestBody ProductDetailsDto productDetailsDto){
        ResponseDto<ProductDetailsOfMerchantDto> responseDto=new ResponseDto<>();
        productDetailsDto.setMerchantId(merchantId);
        try {
            ProductDetails productDetails=productDetailsService.getProductDetails(productDetailsDto.getProductId(),productDetailsDto.getMerchantId());
            final String uri="http://10.177.68.40:8762/spring-cloud-eureka-client-product/product/getProductDetailsOfMerchant";
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProductDetails> entityReq=new HttpEntity<>(productDetails,headers);
            RestTemplate restTemplate=new RestTemplate();
            ProductDetailsOfMerchantDto result=restTemplate.postForObject(uri,entityReq,ProductDetailsOfMerchantDto.class);
            result.setProductDetails(productDetails);
            responseDto.setData(result);
            responseDto.setSuccess(true);
        }catch (Exception exception){
            responseDto.setSuccess(false);
            responseDto.setMessage("Couldn't load details of the product");
            exception.printStackTrace();
        }
        return responseDto;
    }


    @PostMapping("/productdetails/productPrice")
    public ProductDetails getProductPrice(@RequestBody ProductDetailsDto productDetailsDto){
        //productDetailsDto.setMerchantId(merchantId);
        return productDetailsService.getProductDetails(productDetailsDto.getProductId(),productDetailsDto.getMerchantId());
    }



    @GetMapping("/productdetails/merchantProduct")
    public ResponseDto<List<ProductDto>> getProductsSoldByMerchant(@RequestHeader("merchantId") String  merchantId){
        ResponseDto<List<ProductDto>> responseDto=new ResponseDto<>();
        try {
            List<ProductDetails> productDetails= productDetailsService.getProductOfMerchant(merchantId);
            final String uri="http://10.177.68.40:8762/spring-cloud-eureka-client-product/product/listOfProductsByMerchant";
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

    @PostMapping("/productdetails/stockUpdate")
    public boolean updateStock(@RequestBody List<SoldProductsDto> soldProductsDtos){
        return productDetailsService.updateSoldProducts(soldProductsDtos);
    }

    @PostMapping("/productdetails/merchantProductsList")
    public List<MerchantDetailsForProductPageDto> getListOfMerchantsForProduct(@RequestBody String productId){

        List<MerchantDetailsForProductPageDto> merchantList=new ArrayList<>();
        try {
             merchantList= productDetailsService.getMerchantsForProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return merchantList;
    }


}