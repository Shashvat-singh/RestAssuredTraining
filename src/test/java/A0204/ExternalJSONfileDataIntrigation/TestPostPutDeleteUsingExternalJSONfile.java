package A0204.ExternalJSONfileDataIntrigation;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class TestPostPutDeleteUsingExternalJSONfile {
	String userID;
	
	@Test(priority = 1)
	void testPOSTusingExternalJSONfile() throws FileNotFoundException {
		
		File f = new File(".\\bodyPOST.json");
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);
		JSONObject data =  new JSONObject(jt);
		
		Response response =  given().contentType("application/json").body(data.toString()).when().post("http://localhost:3000/students").then().statusCode(201)
		       .body("name", equalTo("Satyam")).body("age", equalTo("18")).body("grade", equalTo("A+++")).body("subjects[0]", equalTo("Sub01"))
		       .body("subjects[1]", equalTo("Sub02")).body("subjects[2]", equalTo("Sub03")).log().all().extract().response();
		userID = response.jsonPath().getString("id");
	}
	
	@Test(priority = 2, dependsOnMethods = "testPOSTusingExternalJSONfile")
	void testPUTusingExternalJSONfile() throws FileNotFoundException {
		File f = new File(".\\bodyPUT.json");
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);
		JSONObject data = new JSONObject(jt);
		
		given().contentType("application/json").body(data.toString()).when().put("http://localhost:3000/students/"+userID).then().statusCode(200)
	       .body("name", equalTo("Shanu")).body("age", equalTo("28")).body("grade", equalTo("B+")).body("subjects[0]", equalTo("Sub001"))
	       .body("subjects[1]", equalTo("Sub002")).body("subjects[2]", equalTo("Sub003")).log().all();
	}
	
	@Test(priority = 3, dependsOnMethods = "testPOSTusingExternalJSONfile")
	void testDELETEusingExternalJSONfile() {
		
		when().delete("http://localhost:3000/students/"+userID).then().statusCode(200).log().all();
	}
	

}
