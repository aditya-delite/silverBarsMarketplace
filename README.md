# silverBars

SilverBarsMarketplace.java is the main class for the demo application. The application will use an
embedded tomcat container running at port 8080 and context path /marketplace
 
  http://localhost:8080/marketplace/order -> post to create an order
  http://localhost:8080/marketplace/order -> delete to cancel an order
  http://localhost:8080/marketplace/summary -> shows summary view
  
  sample json format for order creation and deletion

  {"userId":"user1",
	"quantity":2,
	"price":303,
	"type":"BUY"
	}
