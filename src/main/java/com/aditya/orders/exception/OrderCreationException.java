package com.aditya.orders.exception;

public class OrderCreationException extends GlobalException {

	private static final long serialVersionUID = -7499925676058157863L;

	public OrderCreationException(String message, String details) {
		super(message, details);
	}

}
