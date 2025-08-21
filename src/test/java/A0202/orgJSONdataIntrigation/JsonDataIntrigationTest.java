package A0202.orgJSONdataIntrigation;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class JsonDataIntrigationTest {

	String userID01;
	
	@Test(priority = 1)
	void getDataJsonPOST() {
		JSONObject data = new JSONObject();
		data.put("name", "Shaurya");
		data.put("age", "23");
		data.put("grade", "A+");
		String[] subjects = { "Math", "Science", "Geography" };
		data.put("subjects", subjects);

		Response response = given().contentType("application/json").body(data.toString())
		.when().post("http://localhost:3000/students")
		.then().statusCode(201).body("name", equalTo("Shaurya")).body("age", equalTo("23"))
		.body("grade", equalTo("A+")).body("subjects[0]", equalTo("Math"))
		.body("subjects[1]", equalTo("Science")).body("subjects[2]", equalTo("Geography")).log().all().extract().response();
		userID01 = response.jsonPath().getString("id");
	}

	@Test(priority = 2, dependsOnMethods = "getDataJsonPOST")
	void updateDataJsonPUT() {
		JSONObject data = new JSONObject();

		data.put("name", "UpdatedName");
		data.put("age", "50");
		data.put("grade", "D+");
		String[] subjects = { "UpdSb01", "UpdSb02", "UpdSb03" };
		data.put("subjects", subjects);

		given().contentType("application/json").body(data.toString())
		.when().put("http://localhost:3000/students/" + userID01)
		.then().statusCode(200).body("name", equalTo("UpdatedName")).log().all();
	}

	@Test(priority = 3, dependsOnMethods = "getDataJsonPOST")
	void removeDataJsonDELETE() {
		when().delete("http://localhost:3000/students/" + userID01)
		.then().statusCode(200).log().all();
	}
}
