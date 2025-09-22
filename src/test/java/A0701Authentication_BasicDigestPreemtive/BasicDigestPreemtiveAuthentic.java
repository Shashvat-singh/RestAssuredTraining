package A0701Authentication_BasicDigestPreemtive;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class BasicDigestPreemtiveAuthentic {
	int userBasID;
	int userDigID;
	@Test(priority = 0)
	void testGETBasicAuthentication() {
		// Run your self made express-app for practice ...run BasicAuthenticationServer
		given().auth().basic("adminBas", "passwordBas").when().get("http://localhost:3000/api/basic/emps/").then()
				.statusCode(200).log();
	}

	@Test(priority = 1)
	void testGETDigestAuthentication() {
		// Run your self made express-app for practice ...run BasicAuthenticationServer
		given().auth().digest("adminDig", "passwordDig").when().get("http://localhost:3000/api/digest/emps/").then()
				.statusCode(200).log();
	}

	@Test(priority = 2)
	void testPreemptiveAuthentication() {
		// Run your self made express-app for practice ...run BasicAuthenticationServer
		given().auth().preemptive().basic("adminBas", "passwordBas").when().get("http://localhost:3000/api/basic/emps/")
				.then().statusCode(200).log();
	}

	@Test(priority = 3, dependsOnMethods = "testGETBasicAuthentication")
	void testPOSTinBasicAuth() {
		SetGetBasicDigestPreemtiveAuthentic data = new SetGetBasicDigestPreemtiveAuthentic();
		data.setName("DemoBasic01");
		data.setRole("LalaBasic01");
		data.setSalary(20000);

		Response resBasic = given().auth().basic("adminBas", "passwordBas").body(data).when().post("http://localhost:3000/api/basic/emps/")
				.then().statusCode(201).extract().response();
		userBasID = resBasic.jsonPath().get("id");
		System.out.println(userBasID);
	}

	@Test(priority = 4, dependsOnMethods = "testPOSTinBasicAuth")
	void testDELETEinBasicAuth() {
		given().auth().digest("adminBas", "passwordBas").when().delete("http://localhost:3000/api/basic/emps/"+userBasID)
				.then().statusCode(200).log().all();
	}
	
	@Test(priority = 5, dependsOnMethods = "testGETDigestAuthentication")
	void testPOSTinDigestAuth() {
		HashMap data01 = new HashMap();
		data01.put("name", "DemoDigest01");
		data01.put("role", "LalaDigest01");
		data01.put("salary", 20000);

		Response resDigest = given().auth().basic("adminDig", "passwordDig").body(data01).when().post("http://localhost:3000/api/digest/emps/")
				.then().statusCode(201).extract().response();
		userDigID = resDigest.jsonPath().get("id");
		System.out.println(userDigID);
	}

	@Test(priority = 6, dependsOnMethods = "testPOSTinDigestAuth")
	void testDELETEinDigestAuth() {
		given().auth().basic("adminDig", "passwordDig").when().delete("http://localhost:3000/api/digest/emps/"+userDigID)
				.then().statusCode(200).log().all();
	}
	
	
	
	
}
