package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class ExceptionIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void postRouteTest() {
		given()
				.contentType(ContentType.ANY)
			.when()
				.get("/ittest/exception")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.code());
	}

}