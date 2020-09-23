package com.example.OrderMangement.Controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;
import com.example.OrderMangement.Services.OrderDetailsService;

@RestController
@CrossOrigin(value = "*")
public class OrderDetailsController {
	
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@GetMapping(value = "/premiumCustInfo")
	public ResponseEntity<List<PremiumCustomerInfo>> premiumCustInfo(){
		List<PremiumCustomerInfo> list=orderDetailsService.premiumCustInfo();
		if(CollectionUtils.isEmpty(list)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<PremiumCustomerInfo>>(list, HttpStatus.OK);
		}
	} 
	
	@GetMapping(value = "/couponInfo/{bill}")
	public ResponseEntity<List<CouponInformation>> couponInfo(@PathVariable (value = "bill") @Validated Double bill){
		return Optional.ofNullable(orderDetailsService.couponInfo(bill))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return new ResponseEntity<>(list, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	} 
	
	@GetMapping("/orderdetails/{customerId}")
	public ResponseEntity<List<OrderDetails>> orderDetails(@PathVariable(value = "customerId")
	Integer customerId){
		return Optional.ofNullable(orderDetailsService.orderDetails(customerId))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return new ResponseEntity<>(list, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	}
	
	@GetMapping("/validation/{name}")
	public ResponseEntity<String> orderDetails(@PathVariable(value = "name")
	@Valid	 String name ){
		return Optional.ofNullable(name)
				.map(s->{
					return new ResponseEntity<>(s, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	}
	
	@PutMapping("/updateorder")
	public ResponseEntity<List<OrderDetails>> updateOrder(@RequestBody Customer customer){
		return Optional.ofNullable(orderDetailsService.updateOrder(customer))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(obj->{
					return new ResponseEntity<>(obj, HttpStatus.CREATED);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				});
	}
	
	@GetMapping("/findByType/{type}")
	public ResponseEntity<List<PagingPojo>> findByProductType(@PathVariable(value = "type")
	String productType){
		return Optional.ofNullable(orderDetailsService.findByProductType(productType))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return new ResponseEntity<>(list, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	}

}
