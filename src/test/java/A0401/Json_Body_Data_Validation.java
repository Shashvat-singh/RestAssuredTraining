package A0401;

import static io.restassured.RestAssured.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Json_Body_Data_Validation {

	@Test
	void jsonDataValidation() {

		// Approach :--
//		given().contentType("application/json")
//		
//		.when().get("http://localhost:3000/store")
//		
//		.then().statusCode(200).header("Access-Control-Allow-Headers", "content-type")
//		.body("book[1].title", equalTo("Sword of Honour"))
//		.log().all();

		// Approach 2(Preferred way of approach for validation using this style we can
		// apply loop and conditions.) :----
		Response res = given().contentType("contentType.json").when().get("http://localhost:3000/store");

		System.out.println("Here is the headers list :::::-----");
		Headers headers = res.getHeaders();
		for (Header h : headers) {
			System.out.println(h.getName() + " : " + h.getValue());
		}

		System.out.println("Here is the cookies list :::::-----");
		Map<String, String> cookiesGet = res.cookies();
		for (String k : cookiesGet.keySet()) {
			String cookies_Value = res.getCookie(k);
			System.out.println(k + " : " + cookies_Value);
		}

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(res.header("content-Type"), "application/json");

		String book0Name = res.jsonPath().get("book[0].title").toString();
		Assert.assertEquals(book0Name, "Sayings of the Century");

		String book1Name = res.jsonPath().get("book[1].title").toString();
		Assert.assertEquals(book1Name, "Sword of Honour");

		String book4Name = res.jsonPath().get("book[4].title").toString();
		Assert.assertEquals(book4Name, "To Kill a Mockingbird");
	}

	@Test
	void jsonObjectDataValidation() {
		Response res = given().contentType("application/json").when().get("http://localhost:3000/store");

		JSONObject jo = new JSONObject(res.asString());

		for (int i = 0; i < jo.getJSONArray("book").length(); i++) {
			String bookTitle = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
			System.out.println(bookTitle);
		}
	}

}
