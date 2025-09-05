package A0501ParsingJSONResponseData;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Practice {
	
	@Test
	void pract() {
		System.out.println("-----------------------------------------------------------------");
		File file = new File("C:\\Users\\hds\\eclipse-workspace\\RestAssuredTraining\\src\\test\\resources\\uploadData\\sampleImage01.jpg");
		Response res = 
				given()
				   .multiPart("file", file)
				   //.formParam("description", "This is a test upload")
				.when()
				   .post("http://localhost:3000/upload")
				.then()
				   .statusCode(200)
				   .extract()
				   .response();
		
		System.out.println(res);
		
		
		
		
	}

}
