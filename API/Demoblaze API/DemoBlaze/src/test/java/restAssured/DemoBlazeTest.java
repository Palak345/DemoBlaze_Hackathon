package restAssured;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class DemoBlazeTest {
	
	 private static String authToken = "";
	    private static String cartId = "";
	    private static int productId = 1; 
	    
    //1.Product list
	@Test(priority = 0)
	void getProduct() {
		given()
		.when()
		    .get("https://api.demoblaze.com/entries")
		.then()
		  .statusCode(200)
		  .body("Items",not(empty()))
		  .log().all();
	}
	//2.SignUp
		@Test(priority = 1)
		void Signup() {
			HashMap signupData = new HashMap();
			signupData.put("username", "nas123@");
			signupData.put("password", "qwert");
			
		Response response = given()
			.contentType("application/json")
	        .body(signupData)
	        
			.when()	
			   .post("https://api.demoblaze.com/signup")
			   
			.then()
			.statusCode(201) 
	        .extract()
	        .response();

		System.out.println("Response Body: " + response.getBody().asString());
		}
	
	//2.Login
	@Test(priority = 2)
	void Login() {
		HashMap loginData = new HashMap();
		loginData.put("username", "nas123@");
		loginData.put("password", "qwert");
		
	Response response = given()
		.contentType("application/json")
        .body(loginData)
        
		.when()	
		   .post("https://api.demoblaze.com/login")
		   
		.then()
		.statusCode(200) 
        .extract()
        .response();

	System.out.println("Response Body: " + response.getBody().asString()); // Debugging line
	authToken = response.jsonPath().getString("token");

	}
	 // 3️. Product by Category(test pass)
    @Test(priority = 3)
    void getProductByCategory() {
    	HashMap product = new HashMap();
    	product.put("cat", "phone");
    	given()
        .contentType("application/json")
        .body(product)
    .when()
        .post("https://api.demoblaze.com/bycat")
    .then()
        .statusCode(200)
        .body("Items", not(empty()))
        .log().all();
    	   
    }
    // 4. Add Product to Cart(test fail )
    @Test(priority = 4)
    void addProductToCart() {
    	
    	
    	HashMap cartData = new HashMap();
        cartData.put("id", 1); // Unique cart item ID
        cartData.put("cookie", authToken); // User authentication token
        cartData.put("prod_id", productId);
        cartData.put("flag",true);
    
   Response response = given()
            .contentType("application/json")
            .body(cartData)
        .when()
            .post("https://api.demoblaze.com/addtocart")
        .then()
            .statusCode(200)
            .extract()
            .response();
   System.out.println("Response Status Code " + response.getStatusCode());
   System.out.println("Response Body:" + response.getBody().asString());
    }
    
	
}























