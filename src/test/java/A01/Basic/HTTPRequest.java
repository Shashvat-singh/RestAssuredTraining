package A01.Basic;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

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

	int id;

	@Test(priority = 1)
	void getUsers() {

		when().get("https://reqres.in/api/users?page=2").then().statusCode(200).body("page", equalTo(2))
				.body("total_pages", equalTo(2)).log().all();
	}

	@Test(priority = 2)
	void postUserDetail() {
		HashMap data = new HashMap();
		data.put("name", "DemoName");
		data.put("age", "21");
		data.put("grade", "A+");

		given().contentType("application/json").body(data)

				.when()
				// .headers("x-api-key","reqres-free-v1")
				.post("http://localhost:3000/student").then().statusCode(201).body("name", equalTo("DemoName")).log()
				.all();
	}

	@Test(priority = 3)
	void postReqAndUpdateThroUpdateUser() {
		HashMap data01 = new HashMap();
		data01.put("Name", "Shashvat");
		data01.put("Address", "Sec-62, Noida, UP");

		id = given().contentType("application/json").body(data01)

				.when().header("x-api-key", "reqres-free-v1").post("https://reqres.in/api/users").jsonPath()
				.getInt("id");
	}

	@Test(priority = 4, dependsOnMethods = "postReqAndUpdateThroUpdateUser")
	void updateUser() {
		HashMap data02 = new HashMap();
		data02.put("Name", "Shashvat");
		data02.put("Address", "Samneghat, Lanka, Varanasi, UP");

		given().contentType("application/json").body(data02).when().header("x-api-key", "reqres-free-v1")
				.put("https://reqres.in/api/users/" + id).then().statusCode(201).log().all();
	}

	@Test
	void deleteRequest() {

		when()
				// .header("x-api-key","reqres-free-v1")
				.delete("http://localhost:3000/student/a356")

				.then().statusCode(204).log().all();
	}

}
