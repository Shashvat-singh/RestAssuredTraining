package A0203.POJOdataIntrigation;

import static io.restassured.RestAssured.given;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TestPostPutDeleteUsingPOJOData {

	String userID;
	
	@Test(priority = 0)
	void testPostUsingPOJO() {
		POJO_Request data = new POJO_Request();

		data.setName("Shashvat");
		data.setAge("20");
		data.setGrade("D++");
		String[] subjects = { "Geography", "Science", "Math" };
		data.setSubjects(subjects);

		Response response =  given().contentType("application/json").body(data).when().post("http://localhost:3000/students").then()
				.statusCode(201).body("name", equalTo("Shashvat")).body("age", equalTo("20"))
				.body("grade", equalTo("D++")).body("subjects[0]", equalTo("Geography"))
				.body("subjects[1]", equalTo("Science")).body("subjects[2]", equalTo("Math")).log().all().extract().response();
		
		userID = response.jsonPath().getString("id");

	}

	@Test(priority = 1, dependsOnMethods = "testPostUsingPOJO")
	void testPutUsingPOJO() {
		POJO_Request data = new POJO_Request();

		data.setName("Shaurya");
		data.setAge("21");
		data.setGrade("A++");
		String[] subjects = { "Sub01", "Sub02", "Sub03" };
		data.setSubjects(subjects);

		given().contentType("application/json").body(data).when().put("http://localhost:3000/students/"+userID).then()
				.statusCode(200).body("name", equalTo("Shaurya")).body("age", equalTo("21"))
				.body("grade", equalTo("A++")).body("subjects[0]", equalTo("Sub01"))
				.body("subjects[1]", equalTo("Sub02")).body("subjects[2]", equalTo("Sub03")).log().all();
	}
	@Test(priority = 2, dependsOnMethods = "testPostUsingPOJO")
	void testDELETE(){
		when().delete("http://localhost:3000/students/"+userID).then().statusCode(200).log().all();
	}
}
