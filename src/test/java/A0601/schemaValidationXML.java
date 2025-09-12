package A0601;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class schemaValidationXML {

	void xmlSchemaValidation() {
		given()
		
		
		.when()
		    .get("http://localhost:3000/travelers?page=1")
		
		.then()
		   .assertThat().body(null, null);
		
	}
}
