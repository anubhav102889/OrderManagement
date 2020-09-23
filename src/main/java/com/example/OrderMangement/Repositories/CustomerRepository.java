package com.example.OrderMangement.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Models.PagingPojo;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	public Customer findByCustomerId(Integer id);
	
	@Query("select new com.example.OrderMangement.Models.PagingPojo(cust.customerName,"
			+ " orderdet.productName,orderdet.productType,orderdet.productPrice)"
			+ " from OrderDetails orderdet "
			+ " join Customer cust on orderdet.customer.id=cust.id"
			+ " group by cust.customerName,orderdet.productName,orderdet.productType,orderdet.productPrice")
	public List<PagingPojo> findAllByPageAbleQuery(Pageable pageable);

}
