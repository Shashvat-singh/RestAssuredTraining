package A0703Authentication_ApiKey_Header;


import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class apikey_Header_Authentication {
	
	@Test
	void testGETapikeyHeader() {
		given()
	    .header("x-api-key", "appid") 
	    .queryParam("apiKey", "appvalue")   // Correct key with capital K
	    .pathParams("mypath1", "api")
	    .pathParam("mypath2", "apikey")
	    .pathParam("mypath3", "emps")   //Why 3 pathParem are required :-:apiKey in the route expects a single path segment (e.g., /api/apikey/emps/appkey).
	   
	.when()
	    .get("http://localhost:3000/{mypath1}/{mypath2}/{mypath3}")
	.then()
	    .statusCode(200)
	    .log().all();
	}
}


