package A01.Basic;

import org.testng.annotations.Test;



import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/*
given()
-------> content type, set cookies, add auth, add param, set headers info etc....
(All the "Pre-Reqisite" come under given() section)
     
when()
-------> get, post, put, delete
(All the "Request" come under when() section)

then()
-------> validate status code, extract response, extract headers cookies & response body.....
(All the "Validation" come under then() section)

*/

public class HTTPRequest {

	String userID;

	@Test(priority = 1)
	void getUsers() {

		when().get("https://reqres.in/api/users?page=2").then().statusCode(200).body("page", equalTo(2))
				.body("total_pages", equalTo(2)).headers("Connection", "keep-alive").and()
				.headers("Content-Type", "application/json; charset=utf-8").headers("Content-Encoding", "gzip").log()
				.all();

//		Headers headers = res.getHeaders();
//		
//		for( Header H : headers) {
//			System.out.println(H.getName()+ "  :  "+H.getValue());
//		}

	}

	@Test(priority = 2)
	void postUserDetail() {
		HashMap data = new HashMap();
		data.put("name", "DemoName");
		data.put("age", "21");
		data.put("grade", "A+");

		Response res = given().contentType("application/json").body(data)

				.when().headers("x-api-key", "reqres-free-v1").post("https://reqres.in/api/users").then()
				.statusCode(201).body("name", equalTo("DemoName")).log().all().extract().response();

		String userID = res.jsonPath().getString("id");
	}

	@Test(priority = 3, dependsOnMethods = "postUserDetail")
	void updateUserDetails() {
		HashMap data = new HashMap();
		
		data.put("Name", "Shashvat");
		data.put("Address", "Sec-62, Noida, UP");

		given().contentType("application/json").body(data)
		.when().header("x-api-key", "reqres-free-v1").put("https://reqres.in/api/users/" + userID)
		.then().statusCode(200).body("Name", equalTo("Shashvat")).log().all();

	}

	@Test(priority = 4, dependsOnMethods = "postUserDetail")
	void deleteRequest() {
		given()
		.when().header("x-api-key", "reqres-free-v1").delete("https://reqres.in/api/users/" + userID)
		.then().statusCode(204).log().all();
	}

}
