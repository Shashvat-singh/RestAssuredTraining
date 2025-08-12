package A0301.PathQueryPramenters;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;


public class PathQueryPramet {
	
	//https://reqres.in/api/users?page=2&id=5
	
	@Test
	void testPathQueryPramenters() {
		given()
		    .pathParam("mypath", "users")        //Path Parameters
		    .queryParam("page", 2)               //Query Parameters
		    .queryParam("id", 5)                 //Query Parameters
		.when().header("x-api-key", "reqres-free-v1")
		    .get("https://reqres.in/api/{mypath}")
		
		.then()
		     .statusCode(200)
		     .log().all();
	}	

}
