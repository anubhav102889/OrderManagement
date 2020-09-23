package com.example.OrderMangement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;

@Repository
public interface OrderDetailsRespoitory extends JpaRepository<OrderDetails,Integer> {
	
	@Query(nativeQuery = true)
	public List<PremiumCustomerInfo> premiumCustInfo();
	
	@Query(value = "select new com.example.OrderMangement.Models.CouponInformation(cust.customerName,"
			+ " sum(orderdet.productPrice)) from Customer cust inner join"
			+ " OrderDetails orderdet on orderdet.customer.customerId=cust.customerId"
			+ " group by cust.customerName having sum(orderdet.productPrice)>:bill"
			+ " order by cust.customerName desc")
	public List<CouponInformation> couponInfo(@Param(value = "bill") Double bill);
	
	public List<OrderDetails> findByCustomer_CustomerId(Integer customerId);
	
	@Query(nativeQuery = true)
	public List<PagingPojo> findByProductType(@Param(value = "productType")String type);

}
