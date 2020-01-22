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
    boolean verifyMerchant(String merchantId){
        return merchantService.isMerchant(merchantId);
    }

    @GetMapping("/get/{id}")
    ResponseDto<Merchant> getMerchantDetails(@PathVariable("id") String merchantId){
        ResponseDto<Merchant> responseDto = new ResponseDto<>();
        try{
            Merchant merchantCreated= merchantService.getMerchantDetails(merchantId);
            if(merchantCreated.getMerchantId()!=null)
                responseDto.setData(merchantCreated);
            responseDto.setSuccess(true);
        }catch (Exception e){
            responseDto.setSuccess(false);
            responseDto.setMessage("No such customer exists!!");
        }
        return responseDto;
    }

    @PostMapping("/")
    ResponseDto<Merchant> createMerchant(@RequestBody MerchantDto merchantDto){
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant);
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

}
