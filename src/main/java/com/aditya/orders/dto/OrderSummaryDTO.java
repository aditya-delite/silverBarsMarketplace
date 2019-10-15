package com.aditya.orders.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This is a OrderSummary DTO
 * 
 * @author aditya-gu
 *
 */
@JsonInclude(Include.NON_NULL)
public class OrderSummaryDTO {
	// list of buy orders
	private List<String> buyOrders;
	// list of sell orders
	private List<String> sellOrders;

	/**
	 * @return the buyOrders
	 */
	public List<String> getBuyOrders() {
		return buyOrders;
	}

	/**
	 * @param buyOrders
	 *            the buyOrders to set
	 */
	public void setBuyOrders(List<String> buyOrders) {
		this.buyOrders = buyOrders;
	}

	/**
	 * @return the sellOrders
	 */
	public List<String> getSellOrders() {
		return sellOrders;
	}

	/**
	 * @param sellOrders
	 *            the sellOrders to set
	 */
	public void setSellOrders(List<String> sellOrders) {
		this.sellOrders = sellOrders;
	}

}
