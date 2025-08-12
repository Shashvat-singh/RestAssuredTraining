package A0302.CookiesDemo;

import org.testng.annotations.Test;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class CookiesDemoTst {

	@Test
	void testCookies() {

		Response res = given().when().get("https://www.google.com/");

		// For Getting Single cookie value Actor have to provide the "KEY-eg AEC"
//		String cookies_value =res.getCookie("AEC");
//		System.out.println(cookies_value);

		// For getting Key value of cookies
		Map<String, String> cookies_values = res.getCookies();
//		System.out.println(cookies_values.keySet()); //For Key of Cookie

		for (String k : cookies_values.keySet()) {
			String cookie_value = res.getCookie(k);
			System.out.println(k + ">>>>has value>>>>" + cookie_value);
		}
	}

	@Test
	void testServer() {
		Response res = given().when().get("https://www.google.co.in");
		res.getHeaders().forEach(header -> System.out.println(header.getName() + " : " + header.getValue()));

	}

}
