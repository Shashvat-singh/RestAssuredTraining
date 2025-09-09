package A0601;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XMLTokener;
import org.testng.annotations.Test;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;


public class Practice {
	@Test
	void getJSONObj() {
		
		Response res = given().header("x-api-key", "MY_SECRET_KEY_123")
				.when().get("http://localhost:3000/travelers?page=1").then().statusCode(200).extract().response();
		
		System.out.println(res.getHeader("content-type"));
	}
	
}
