package com.project.merchantmicroservice.controller;


import com.project.merchantmicroservice.dto.MerchantDto;
import com.project.merchantmicroservice.dto.ResponseDto;
import com.project.merchantmicroservice.entity.Merchant;
import com.project.merchantmicroservice.service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @GetMapping("/verify")
    ResponseDto<String> verifyMerchant(@RequestHeader("merchantId") String merchantId){
        ResponseDto<String> responseDto=new ResponseDto<>();
        responseDto.setSuccess(merchantService.isMerchant(merchantId));
        return responseDto;
    }

    @GetMapping("/get")
    ResponseDto<Merchant> getMerchantDetails(@RequestHeader("merchantId") String merchantId){
     //   merchantService.computeRating();
        ResponseDto<Merchant> responseDto = new ResponseDto<>();
        try{
            Merchant merchantCreated= merchantService.getMerchantDetails(merchantId);
            if(merchantCreated.getMerchantId()!=null)
                responseDto.setData(merchantCreated);
            responseDto.setSuccess(true);
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("No such merchant exists!!");
        }
        return responseDto;
    }

    @PostMapping("/add")
    ResponseDto<Merchant> createMerchant(@RequestHeader("merchantId") String merchantId,@RequestHeader("merchantEmail") String merchantEmail,@RequestBody MerchantDto merchantDto){
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant);
        merchant.setMerchantId(merchantId);
        merchant.setEmail(merchantEmail);
        ResponseDto<Merchant> responseDto = new ResponseDto<>();
        try{
            Merchant merchantCreated= merchantService.updateMerchant(merchant);
            responseDto.setData(merchantCreated);
            responseDto.setSuccess(true);
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Merchant is not created!!");
        }
        return responseDto;
    }


    @PostMapping("/update")
    ResponseDto<Merchant> updateMerchant(@RequestHeader("merchantId") String merchantId,@RequestBody MerchantDto merchantDto){
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant);
        merchant.setMerchantId(merchantId);
        ResponseDto<Merchant> responseDto = new ResponseDto<>();
        try{
            Merchant merchantCreated= merchantService.updateMerchant(merchant);
            merchantCreated.setMerchantId("");
            responseDto.setData(merchantCreated);
            responseDto.setSuccess(true);
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("Merchant is not created!!");
        }
        return responseDto;
    }

}
