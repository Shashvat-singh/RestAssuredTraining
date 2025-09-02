package A0401;

import static io.restassured.RestAssured.*;



import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class searchTitleForDataValid {
	
	@Test
	void searchForSingleTitle() {
		boolean Status = false;
		String expectedBookTitle = "To Kill a Mockingbird";
		Response res = given().contentType("application/json").when().get("http://localhost:3000/store").then().statusCode(200).log().all().extract().response();
		
		JSONObject jo = new JSONObject(res.asString());
		
		for(int i=0; i < jo.getJSONArray("book").length(); i++) {
			if(jo.getJSONArray("book").getJSONObject(i).get("title").toString().equalsIgnoreCase(expectedBookTitle)) {
				Status = true;
				break;
			}
		}
		
		Assert.assertEquals(Status, true);
		
		
		
		
	}

}
