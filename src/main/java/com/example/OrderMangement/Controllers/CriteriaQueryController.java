package com.example.OrderMangement.Controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;
import com.example.OrderMangement.Services.CriteriaQueryService;

@RestController
public class CriteriaQueryController {
	
	@Autowired
	private CriteriaQueryService criteriaQueryService;
	
	@GetMapping("/findBillUsingCriteria/{bill}")
	public ResponseEntity<List<CouponInformation>> findBillUsingCriteria(@PathVariable(value = "bill")
	Double bill){
		return Optional.ofNullable(criteriaQueryService.findBillUsingCriteria(bill))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return new ResponseEntity<>(list, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	}
	
	@GetMapping("/orderdetails/{customerId}/{orderId}")
	public ResponseEntity<List<OrderDetails>> findOrderDetailsUsingCriteriaQuery(@PathVariable(value = "customerId")Integer customerId,
			@PathVariable(value = "orderId")	Integer orderId) {
		return Optional.ofNullable(criteriaQueryService.findOrderDetailsUsingCriteriaQuery(customerId, 
				orderId))
		.filter(list->!CollectionUtils.isEmpty(list))
		.map(list->{
			return new ResponseEntity<>(list,HttpStatus.OK);
		})
		.orElseGet(()->{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		});
		
	}
	
		@GetMapping("/premiumCustInfoUsingCriteria")
		public ResponseEntity<List<PremiumCustomerInfo>> premiumCustomerInfoUsingCriteria(){
			List<PremiumCustomerInfo> result=criteriaQueryService.premiumCustomerInfoUsingCriteria();
			if(Objects.isNull(result)) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<>(result, HttpStatus.OK);
			}	
	}
		@GetMapping("/emnativequery/{customerId}")
		public ResponseEntity<List<PagingPojo>> entityManagerNativeQuery(@PathVariable(value = "customerId")
		Integer customerId){
			return Optional.ofNullable(criteriaQueryService.entityManagerNativeQuery(customerId))
					.filter(list->!CollectionUtils.isEmpty(list))
					.map(list->{
						return new ResponseEntity<>(list, HttpStatus.OK);
					})
					.orElseGet(()->{
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					});
		}

}
