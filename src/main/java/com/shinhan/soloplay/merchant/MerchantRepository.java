package com.shinhan.soloplay.merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantRepository extends JpaRepository<MerchantEntity,String>{

	@Query("SELECT m.merchantName FROM #{#entityName} m WHERE m.merchantId = :merchantId")
	String findMerchantNameById(String merchantId);
}
