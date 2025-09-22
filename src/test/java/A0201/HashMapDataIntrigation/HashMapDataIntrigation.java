package A0201.HashMapDataIntrigation;

import java.util.HashMap;



import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

//HashMap
//org.json

public class HashMapDataIntrigation {

	String userID01;

	@Test(priority = 1)
	void createDataHashMapPOST() {

		HashMap data = new HashMap();
		data.put("name", "Shashvat");
		data.put("age", "29");
		data.put("grade", "A++");
		String[] subjects = { "Math", "Science", "History" };
		data.put("subjects", subjects);

		Response response = given().contentType("application/json").body(data)

				.when().post("http://localhost:3000/students").then().statusCode(201).body("name", equalTo("Shashvat"))
				.body("age", equalTo("29")).body("grade", equalTo("A++")).body("subjects[0]", equalTo("Math"))
				.body("subjects[1]", equalTo("Science")).body("subjects[2]", equalTo("History")).log().all().extract()
				.response();

		userID01 = response.jsonPath().getString("id");
	}

	@Test(priority = 2, dependsOnMethods = "createDataHashMapPOST")
	void updateDataHashMapPUT() {
		HashMap data = new HashMap();
		data.put("name", "UpdatedName");
		data.put("age", "11");
		data.put("grade", "D++");
		String[] subjects = { "UpdSub01", "UpdSub02", "UpdSub03" };
		data.put("subjects", subjects);

		given().contentType("application/json").body(data)

				.when().put("http://localhost:3000/students/" + userID01)

				.then().statusCode(200).body("name", equalTo("UpdatedName")).body("age", equalTo("11"))
				.body("grade", equalTo("D++")).body("subjects[0]", equalTo("UpdSub01"))
				.body("subjects[1]", equalTo("UpdSub02")).body("subjects[2]", equalTo("UpdSub03")).log().all().extract()
				.response();
	}

	@Test(priority = 3, dependsOnMethods = "createDataHashMapPOST")
	void deleteDataHashMapDELETE() {
		when().delete("http://localhost:3000/students/" + userID01).then().statusCode(200).log().all();
	}

}
