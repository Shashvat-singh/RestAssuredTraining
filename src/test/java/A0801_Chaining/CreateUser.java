package A0801_Chaining;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.github.javafaker.Faker;

import io.restassured.response.Response;

public class CreateUser {
	
	@Test
	void test_createUser() {
		Faker faker = new Faker();
		
		JSONObject data = new JSONObject();
		data.put("name", faker.name().fullName());
		data.put("gender", faker.demographic().sex());
		data.put("age", faker.date().birthday(21, 35));
		
		String bearerToken = "mySecretBearerToken";
		
		Response res = given()
		      .contentType("application/json")
		      .headers("Authorization", "Bearer "+bearerToken)
		      .body(data.toString())
		.when()
		      .post("http://localhost:3000/api/bearer/emps")
		.then()
		     .statusCode(201)
		     .extract().response();
		
		String userId = res.jsonPath().getString("id");
		System.out.println(userId);
	}

}
