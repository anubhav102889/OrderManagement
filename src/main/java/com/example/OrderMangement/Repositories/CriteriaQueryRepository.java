package com.example.OrderMangement.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.OrderMangement.Entities.Customer;
import com.example.OrderMangement.Entities.OrderDetails;
import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Models.PagingPojo;
import com.example.OrderMangement.Models.PremiumCustomerInfo;

@Repository
public class CriteriaQueryRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<CouponInformation> findBillUsingCriteria(Double bill){
		
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponInformation> cq=cb.createQuery(CouponInformation.class);
		Root<Customer> cust=cq.from(Customer.class);
		Join<Customer,OrderDetails> orderdet=cust.join("ordersList");
		
		cq.select(cb.construct(CouponInformation.class, cust.get("customerName"),
				cb.sum(orderdet.<Double> get("productPrice"))))
		.groupBy(cust.get("customerName"))
		.having(cb.greaterThanOrEqualTo(cb.sum(orderdet.<Double> get("productPrice")),bill))
		.orderBy(cb.desc(cust.get("customerName")));
		TypedQuery<CouponInformation>query=entityManager.createQuery(cq);
		List<CouponInformation> list=query.getResultList();

		return list;
		
	}
	public List<OrderDetails> findOrderDetailsUsingCriteriaQuery(Integer customerId,Integer orderId) {
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderDetails> cq=cb.createQuery(OrderDetails.class);
		Root<OrderDetails> orderDet=cq.from(OrderDetails.class);
		//Join<OrderDetails,Customer> cust=orderDet.join("customer");
		cq.select(orderDet);
		List<Predicate> predicates=new ArrayList<>();
		if(Objects.nonNull(customerId) && customerId>0) {
			predicates.add(cb.equal(orderDet.get("customer"),customerId));
		}
		if(Objects.nonNull(orderId) && orderId>0) {
			predicates.add(cb.equal(orderDet.get("orderId"),orderId));
		}
		
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		
		TypedQuery<OrderDetails> query=entityManager.createQuery(cq);
		List<OrderDetails> list=query.getResultList();
		return list;
	}
	public List<PremiumCustomerInfo> premiumCustomerInfoUsingCriteria(){
		
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<PremiumCustomerInfo> cq=cb.createQuery(PremiumCustomerInfo.class);
		Root<Customer> root=cq.from(Customer.class);
		Join<Customer,OrderDetails> orderJoin=root.join("ordersList");
		cq.multiselect(root.get("customerName"),cb.selectCase()
				.when(cb.greaterThan(cb.count(orderJoin.get("customer")), 2l),"Yes")
				.otherwise("N"))
		.groupBy(root.get("customerName"));
	TypedQuery<PremiumCustomerInfo> query=entityManager.createQuery(cq);
	List<PremiumCustomerInfo> list=query.getResultList();
		return list;
	}
	
	public List<PagingPojo> entityManagerNativeQuery(Integer customerId){
		
		Query query=entityManager.createNativeQuery("select cust.name,orderdet.name as productName,"
				+ " orderdet.type,orderdet.price from customer_tbl cust,"
				+ " orderdetails_tbl orderdet where cust.id=orderdet.customerId"
				+ " and cust.id=:customerId", "PagingMapping");
		query.setParameter("customerId", customerId);
		List<PagingPojo> result=query.getResultList();
		return result;
		
	}
}
