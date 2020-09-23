package com.example.OrderMangement.Controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Services.CustomerService;

@RestController
@CrossOrigin(value = "*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Customer customer){
		return Optional.ofNullable(customerService.saveCustomerOrder(customer))
				.filter(id->id>0)
				.map(id->{
					HttpHeaders headers = new HttpHeaders();
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}").buildAndExpand(id)
							.toUri();
					headers.setLocation(location);
					return new ResponseEntity<String>("Created Successfully", headers, HttpStatus.CREATED);
				})
				.orElseGet(()->{
					return new ResponseEntity<>("Internal Server Error Occurred",HttpStatus.INTERNAL_SERVER_ERROR);
				});
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Customer> findByCustomerId(@PathVariable(value = "customerId")
	Integer customerId) {
		return Optional.ofNullable(customerService.findByCustomerId(customerId))
				.filter(cust->{
					return !StringUtils.isEmpty(cust.getCustomerName())
							&& !CollectionUtils.isEmpty(cust.getOrdersList());
				})
				.map(cust->{
					return new ResponseEntity<>(cust, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				});
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> customers(){
		return  Optional.ofNullable(customerService.customers())
		.map(list->{
			return new ResponseEntity<>(list, HttpStatus.OK);
		})
		.orElseGet(()->{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		});
		
	}
	
	@GetMapping("/pageAble")
	public ResponseEntity<List<PagingPojo>> findAllByPageAbleQuery(Pageable pageable){
		return Optional.ofNullable(customerService.findAllByPageAbleQuery(pageable))
				.filter(list->!CollectionUtils.isEmpty(list))
				.map(list->{
					return new ResponseEntity<>(list, HttpStatus.OK);
				})
				.orElseGet(()->{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				});
	}

}
