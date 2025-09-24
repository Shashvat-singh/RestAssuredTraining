package A0501ParsingXML_ResponseData;

import static io.restassured.RestAssured.*;


import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ParsingXMLResponse {

	@Test
	void testXMLResponse() {

		/*
		 * //Approach --01 given() .when() .get("http://localhost:3000/travelers")
		 * .then() .statusCode(200) .headers("Content-Type" ,
		 * "application/xml; charset=utf-8") .headers("X-Powered-By", "Express")
		 * .cookie("user", "TestUser") .cookie("sessionId", "ABC123XYZ")
		 * .body("TravelerInformationResponse.page", equalTo("1"))
		 * .body("TravelerInformationResponse.travelers.Travelerinformation[1].name",
		 * equalTo("Vinod Sharma"));
		 */

		Response res = given().when().get("http://localhost:3000/travelers?page=1").then().extract().response();

		// Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");

		Assert.assertEquals(res.xmlPath().get("TravelerInformationResponse.page").toString(), "1");

		Assert.assertEquals(
				res.xmlPath().get("TravelerInformationResponse.trave lers.Travelerinformation[1].name").toString(),
				"Vinod Sharma");
	}

	@Test
	void testXMLResponseBody() {
		Response res = given()
				.when().get("http://localhost:3000/travelers?page=1").then().extract().response();

		XmlPath xp = new XmlPath(res.asString());
		List<Object> travellers = xp.getList("TravelerInformationResponse.travelers.Travelerinformation");
		Assert.assertEquals(travellers.size(), 06);
		
		//verify traveller name is present
		

	}
	@Test
	void testXMLResponseBodyData() {
		
		Response res = given().when().get("http://localhost:3000/travelers?page=1").then().statusCode(200).extract().response();
		 
		XmlPath xp = new XmlPath(res.asString());
		List<String> traveller = xp.getList("TravelerInformationResponse.travelers.Travelerinformation.name");
		
//		for(String k : traveller) {
//			System.out.println(k);
//		}
		
		for(int i =0; i < traveller.size(); i++) {
			System.out.println(traveller.get(i));
		}
		
	}
}
