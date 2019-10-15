package com.aditya.orders.service.entity;

import com.aditya.orders.service.OrderType;

/**
 * This is an order entity class
 * 
 * @author aditya-gu
 *
 */
public class OrderEntity {
	// userid
	private String userId;
	// quantity
	private double quantity;
	// price per kg
	private int price;
	// order tpye
	private OrderType type;

	public OrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the type
	 */
	public OrderType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(OrderType type) {
		this.type = type;
	}

}
