package com.shinhan.soloplay.merchant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {
	
	@Autowired
	MerchantRepository merchantRepository;

	@Override
	public List<MerchantDTO> getMerchantById(String merchantID) {
		List<MerchantDTO> merchantDTO = merchantRepository
				.findById(merchantID)
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
				
		return merchantDTO;
	}



}
