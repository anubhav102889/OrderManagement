package com.example.OrderMangement.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Repositories.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Integer saveCustomerOrder(Customer customer) {
		return Optional.ofNullable(customer)
				.filter(obj->!StringUtils.isEmpty(obj.getCustomerName())
						&& !CollectionUtils.isEmpty(obj.getOrdersList()))
				.map(obj->{
					obj.getOrdersList().stream().forEach(order->{
						order.setCustomer(obj);
					});
					Customer customerSaved =customerRepository.save(obj);
					if(customerSaved.getCustomerId()>0) {
						return customer.getCustomerId();
					}
					else {
						return 0;
					}
				})
				.orElse(null);
	}
	
	public Customer findByCustomerId(Integer customerId) {
		return Optional.ofNullable(customerId)
				.filter(id->id>0)
				.map(id->{
					return customerRepository.findByCustomerId(id);
				})
				.orElse(null);
	}
	
	public List<Customer> customers(){
		return Optional.ofNullable(customerRepository.findAll())
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return list;
				})
				.orElse(null);
	}
	
	public List<PagingPojo> findAllByPageAbleQuery(Pageable pageable){
		return Optional.ofNullable(pageable)
		.filter(p->{
			return p.getPageSize()>0 && 
					!Optional.ofNullable(p.getSort()).get().toString().equalsIgnoreCase("unsorted");
		})
		.map(p->{
			return customerRepository.findAllByPageAbleQuery(p);
		})
		.orElse(null);
	}

}
