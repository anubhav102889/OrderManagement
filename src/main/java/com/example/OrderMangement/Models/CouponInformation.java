package com.example.OrderMangement.Models;

public class CouponInformation {
	
	private String customerName;
	
	private Double customerBill;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getCustomerBill() {
		return customerBill;
	}

	public CouponInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setCustomerBill(Double customerBill) {
		this.customerBill = customerBill;
	}

	public CouponInformation(String customerName, Double customerBill) {
		super();
		this.customerName = customerName;
		this.customerBill = customerBill;
	}

}
