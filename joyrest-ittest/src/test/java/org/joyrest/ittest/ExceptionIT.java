package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

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
	public void badRequest() {
		given()
				.contentType(ContentType.ANY)
			.when()
				.get("/ittest/exception/badRequest")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.code());
	}

	@Test
	public void numberFormatWithBody() {
		given()
				.accept(ContentType.JSON)
				.contentType(ContentType.ANY)
			.when()
				.get("/ittest/exception/numberFormat")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.BAD_REQUEST.code())
				.body("title", equalTo("My Feed Title"))
				.body("description", equalTo("My Feed Description"))
				.body("link", equalTo("http://localhost:8080"));

	}

}