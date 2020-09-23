package com.example.OrderMangement.Services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;
import com.example.OrderMangement.Repositories.CriteriaQueryRepository;

@Service
public class CriteriaQueryService {
	
	@Autowired
	private CriteriaQueryRepository criteriaQueryRepository;
	
	public List<CouponInformation> findBillUsingCriteria(Double bill){
		return Optional.ofNullable(bill)
				.filter(tariff->tariff>0.0)
				.map(tariff->{
					return criteriaQueryRepository.findBillUsingCriteria(tariff);
				})
				.orElse(null);
	}
	
	public List<OrderDetails> findOrderDetailsUsingCriteriaQuery(Integer customerId,Integer orderId) {
		
		if((Objects.nonNull(customerId) || Objects.nonNull(orderId)) && (customerId>0 || orderId>0)) {
			return criteriaQueryRepository.findOrderDetailsUsingCriteriaQuery(customerId, orderId);
		}
		else {
			return null;
		}
	}
	
	public List<PremiumCustomerInfo> premiumCustomerInfoUsingCriteria(){
		return Optional.ofNullable(criteriaQueryRepository.premiumCustomerInfoUsingCriteria())
		.filter(list->!CollectionUtils.isEmpty(list))
		.map(list->{
			return list;
		})
		.orElse(null);
	}
	
	public List<PagingPojo> entityManagerNativeQuery(Integer customerId){
		return Optional.ofNullable(customerId)
				.filter(id->id>0)
				.map(id->{
					return criteriaQueryRepository.entityManagerNativeQuery(id);
				})
				.orElse(null);
	}
}
