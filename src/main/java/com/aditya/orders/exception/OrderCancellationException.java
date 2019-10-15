package com.aditya.orders.exception;

public class OrderCancellationException extends GlobalException {

	private static final long serialVersionUID = -5565954663220931438L;

	public OrderCancellationException(String message, String details) {
		super(message, details);
	}

}
