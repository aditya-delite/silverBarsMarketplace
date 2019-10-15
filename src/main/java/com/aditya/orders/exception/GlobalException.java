package com.aditya.orders.exception;

/**
 * This is a custom global exception class
 * 
 * @author aditya-gu
 *
 */
public class GlobalException extends Exception {
	private static final long serialVersionUID = 9079937375154301403L;
	private String details;
	private String message;

	public GlobalException(String message, String details) {
		super();
		this.details = details;
		this.message = message;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}