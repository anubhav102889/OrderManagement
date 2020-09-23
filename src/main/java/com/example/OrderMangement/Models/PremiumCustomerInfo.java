package com.example.OrderMangement.Models;

public class PremiumCustomerInfo {
	
	private String customerName;
	
	private String IsPremiumCustomerInfo;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIsPremiumCustomerInfo() {
		return IsPremiumCustomerInfo;
	}

	public void setIsPremiumCustomerInfo(String isPremiumCustomerInfo) {
		IsPremiumCustomerInfo = isPremiumCustomerInfo;
	}

	public PremiumCustomerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PremiumCustomerInfo(String customerName, String isPremiumCustomerInfo) {
		super();
		this.customerName = customerName;
		IsPremiumCustomerInfo = isPremiumCustomerInfo;
	}

	
	
	

}
