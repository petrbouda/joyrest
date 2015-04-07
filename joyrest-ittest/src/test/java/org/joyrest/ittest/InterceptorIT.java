package org.joyrest.ittest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class InterceptorIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRoutePost_NoPathWithBody() {
		given().body(feedEntity)
			.when()
			.contentType(ContentType.JSON)
			.post("/ittest/aspect")
			.then().statusCode(HttpStatus.OK.code());
	}

}
