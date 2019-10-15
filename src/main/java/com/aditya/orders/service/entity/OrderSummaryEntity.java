package com.aditya.orders.service.entity;

import com.aditya.orders.service.OrderType;

/**
 * This represents a order summery entity
 * 
 * @author aditya-gu
 *
 */
public class OrderSummaryEntity {
	private OrderType type;
	private double quantity;
	private int price;

	public OrderSummaryEntity() {
		super();
	}

	public OrderSummaryEntity(OrderType type, double quantity, int price) {
		super();
		this.type = type;
		this.quantity = quantity;
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

}
