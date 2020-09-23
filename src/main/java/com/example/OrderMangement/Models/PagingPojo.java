package com.example.OrderMangement.Models;

public class PagingPojo {
	
	private String customerName;
	
	private String productName;
	
	private String productType;
	
	private Double productPrice;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public PagingPojo(String customerName, String productName, String productType, Double productPrice) {
		super();
		this.customerName = customerName;
		this.productName = productName;
		this.productType = productType;
		this.productPrice = productPrice;
	}

	public PagingPojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
