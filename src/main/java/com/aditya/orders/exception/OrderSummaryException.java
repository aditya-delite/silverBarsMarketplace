package com.aditya.orders.exception;

public class OrderSummaryException extends GlobalException {

	private static final long serialVersionUID = -7499925676058157863L;

	public OrderSummaryException(String message, String details) {
		super(message, details);
	}

}
