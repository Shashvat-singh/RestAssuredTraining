package A0601SchemaValidation;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;


public class schemaValidationJSON {
	
	@Test
	void jsonScemaValidation() {
		given()
		  
		.when()
		   .get("http://localhost:3000/store")
		
		.then()
		   .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("storeJsonSchema.json"))
		   .statusCode(200);
	}

}
