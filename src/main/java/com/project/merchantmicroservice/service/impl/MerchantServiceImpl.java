package com.project.merchantmicroservice.service.impl;

import com.project.merchantmicroservice.dto.ProductDto;
import com.project.merchantmicroservice.dto.RatingDto;
import com.project.merchantmicroservice.entity.Merchant;
import com.project.merchantmicroservice.entity.ProductDetails;
import com.project.merchantmicroservice.repository.MerchantRepository;
import com.project.merchantmicroservice.service.MerchantService;
import com.project.merchantmicroservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    ProductDetailsService productDetailsService;

    @Transactional
    @Override
    public Merchant updateMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public boolean isMerchant(String id) {
        return merchantRepository.existsById(id);
    }

    @Override
    public Merchant getMerchantDetails(String merchantId) {
        return (merchantRepository.existsById(merchantId))?merchantRepository.findById(merchantId).get():null;
    }


    @Async
    @Transactional
    @Override
    public void computeRating() {
        Iterable<Merchant> merchantList=merchantRepository.findAll();
        List<RatingDto> ratingDtos=new ArrayList<>();

//        final String uri="http://10.177.69.50:8762/spring-cloud-eureka-client-product/product/";
//        HttpHeaders headers=new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entityReq=new HttpEntity<>("",headers);
//        RestTemplate restTemplate=new RestTemplate();
//        List<ProductDto> result=restTemplate.postForObject(uri,entityReq,List.class);

        int max=0;
        for (Merchant merchant:merchantList) {
            int total=0;
            List<ProductDetails> productDetailsOfMerchant =productDetailsService.getProductOfMerchant(merchant.getMerchantId());
            RatingDto ratingDto=new RatingDto();
            ratingDto.setMerchantId(merchant.getMerchantId());
            total+=productDetailsOfMerchant.size();
            total+=merchant.getMerchantRating();
            for (ProductDetails productDetail:productDetailsOfMerchant) {
                total+=productDetail.getProductQuantity();
                total+=productDetail.getTotalProductsSold();
                total+=productDetail.getProductPrice();
            }
            if (total>max)
                max=total;
            ratingDto.setTotal(total);
            ratingDtos.add(ratingDto);
        }
        float rating=0;
        for (RatingDto ratingDto:ratingDtos) {
            rating=((ratingDto.getTotal()*5)/max);
            merchantRepository.updateMerchantRating(ratingDto.getMerchantId(),rating);
            ratingDto.setMerchantRating(rating);
        }
    }
}
