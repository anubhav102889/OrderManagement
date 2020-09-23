package com.example.OrderMangement.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;
import com.example.OrderMangement.Repositories.OrderDetailsRespoitory;

@Service
public class OrderDetailsService {
	
	@Autowired
	private OrderDetailsRespoitory orderDetailsRespoitory;
	
	public List<PremiumCustomerInfo> premiumCustInfo(){
		return Optional.ofNullable(orderDetailsRespoitory.premiumCustInfo())
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return list;
				})
				.orElse(null);
	}
	
	public List<CouponInformation> couponInfo(Double bill){
		return Optional.ofNullable(bill)
				.filter(price->price>0.0)
				.map(price->{
					return orderDetailsRespoitory.couponInfo(price);
				})
				.orElse(null);
	}
	
	public List<OrderDetails> orderDetails(Integer customerId){
		return Optional.ofNullable(customerId)
				.filter(id->id>0)
				.map(id->{
					return orderDetailsRespoitory.findByCustomer_CustomerId(id);
				})
				.orElse(null);
	}
	
	public List<OrderDetails> updateOrder(Customer customer){
		return Optional.ofNullable(customer)
				.filter(obj->Objects.nonNull(obj.getCustomerId()) && obj.getCustomerId()>0
						&& !CollectionUtils.isEmpty(obj.getOrdersList().stream().filter(order->Objects.nonNull(order.getOrderId())
								&& order.getOrderId()>0).collect(Collectors.toList())))
				.map(obj->{
					List<OrderDetails>  result=new ArrayList<>();
					for(OrderDetails order:obj.getOrdersList()) {
						order.setCustomer(obj);
						OrderDetails orderUpdated=orderDetailsRespoitory.save(order);
						if(order.getOrderId()>0) {
							result.add(orderUpdated);
						}
					}
					return result;
				})
				.orElse(null);
	}
	
	public List<PagingPojo> findByProductType(String producType){
		return Optional.ofNullable(producType)
				.filter(type->!StringUtils.isEmpty(type))
				.map(type->{
					//List<PagingPojo> result=new ArrayList<>();
					return orderDetailsRespoitory.findByProductType(type);
					 
				})
				.orElse(null);
	}

}
