package A0302.Cookies_Header_Demo;

import static io.restassured.RestAssured.*;


import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class getSingleHeaderDemoTest {

	@Test
	void assertHeader() {
		Response res = given().when().get("https://reqres.in/api/users").then().statusCode(200)
				.header("Content-Type", "application/json; charset=utf-8").and().header("Transfer-Encoding", "chunked")
				.and().header("Content-Encoding", "gzip").log().all().extract().response();
	}

	@Test
	void getSpecificHeaderName_Value() {
		Response res = given().when().get("https://reqres.in/api/users");

		// get single header info :---
		String contentLengthHeader = res.getHeader("Content-size");
		System.out.println("The content type value(Header Value) :>>>>" + contentLengthHeader);
	}

	@Test
	void getAllHeadersName_Value() {
		Response res = given().when().get("https://reqres.in/api/users");

		Headers headers = res.getHeaders();

		System.out.println("Headers List are ::::::::>>>>>>>>>>>>>>>>>>>>>>");

		//res.getHeaders().forEach(header -> System.out.println(header.getName()+" : "+ header.getValue()));

		for (Header h : headers) {
			System.out.println(h.getName() + " : " + h.getValue());
		}

	}

}
