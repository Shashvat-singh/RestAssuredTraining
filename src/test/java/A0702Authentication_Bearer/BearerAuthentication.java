package A0702Authentication_Bearer;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;


public class BearerAuthentication {
	
	@Test
	void testGETinBearerToken() {
		String bearerToken = "mySecretBearerToken";
		
		given()
		      .header("Authorization", "Bearer "+bearerToken)
		.when()
		      .get("http://localhost:3000/api/bearer/emps")
		 .then()  
		      .statusCode(200)
		      .log().all();
	}
	
	
	void testGETinOauth1() {
		given()
		      .auth().oauth("consumerKey", "consumerSecrat", "accessToken", "tokenSecrate")
		.when()
		      .get("url")
		.then()
		      .statusCode(200)
		      .log().all();
	}
	
	@Test
	void testGETinOauth2() {
		given()
		      .auth().oauth2("mySecretBearerToken")
	    .when()
		      .get("http://localhost:3000/api/bearer/emps")
		.then()
		      .statusCode(200)
		      .log().all();
	}

}
