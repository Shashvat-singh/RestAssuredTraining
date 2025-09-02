package A0302.Cookies_Header_Demo;

import org.testng.annotations.Test;


import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.NameAndValue;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class CookiesHeaderDemoTest {

	@Test
	void getCookiesListTest() {

		Response res = given().when().get("https://www.google.com/");

		// For Getting Single cookie value Actor have to provide the "KEY-eg AEC"
//		String cookies_value =res.getCookie("AEC");
//		System.out.println(cookies_value);

		// For getting Key value of cookies
		Map<String, String> cookies_key_values = res.getCookies();
//		System.out.println(cookies_values.keySet()); //For Key of Cookie
		System.out.println("Cookies List are ::::::::>>>>>>>>>>>>>>>>>>>>>>");
		for (String k : cookies_key_values.keySet()) {
			String cookie_value = res.getCookie(k);
			System.out.println(k + ">>>>has value>>>>" + cookie_value);
		}
	}

	

}
