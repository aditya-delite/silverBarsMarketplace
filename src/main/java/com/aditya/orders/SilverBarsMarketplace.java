package com.aditya.orders;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * This is the main class for the demo application. The application will use an
 * embedded tomcat container running at port 8080 and context path /marketplace
 * 
 * http://localhost:8080/marketplace/order -> post to create an order
 * http://localhost:8080/marketplace/order -> delete to cancel an order
 * http://localhost:8080/marketplace/summary -> shows summary view
 * 
 * sample json format for order creation and deletion
 * 
 * {"userId":"user1",
	"quantity":2,
	"price":303,
	"type":"BUY"
	}
	
 * @author aditya-gu
 *
 */
@SpringBootApplication
public class SilverBarsMarketplace {

	public static void main(String[] args) {
		SpringApplication.run(SilverBarsMarketplace.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
