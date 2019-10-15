package com.aditya.orders.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.aditya.orders.service.OrderType;

/**
 * This is a order DTO class
 * 
 * @author aditya-gu
 *
 */
public class OrderDTO {
	// user id
	@NotEmpty
	@NotNull(message = "user id cannot be null")
	private String userId;
	// quantity
	@Min(message = "quantity must be greater than or equal to 1", value = 1)
	@Max(message = "quantity must be less than or equal to 10000", value = 10000)
	private double quantity;
	// price per kg
	@Min(message = "Order price must be greater than 0", value = 1)
	@Max(message = "Order price must be less than or equal to 10000", value = 10000)
	private int price;
	// order type
	private OrderType type;

	public OrderDTO() {
		super();
	}

	public OrderDTO(@NotEmpty String userId, @Positive double quantity, @Positive int price, @NotNull OrderType type) {
		super();
		this.userId = userId;
		this.quantity = quantity;
		this.price = price;
		this.type = type;
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
