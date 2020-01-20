package com.project.merchantmicroservice.service.impl;

import com.project.merchantmicroservice.entity.Merchant;
import com.project.merchantmicroservice.repository.MerchantRepository;
import com.project.merchantmicroservice.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

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
}
