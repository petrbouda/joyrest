package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import com.jayway.restassured.http.ContentType;
import org.joyrest.ittest.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class GetRouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRouteGet_WithoutPath() {
		given()
			.when()
				.get("/ittest/route")
			.then()
				.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteGet_WithPath() {
		given()
			.when()
				.get("/ittest/route/withPath")
			.then()
				.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteGet_WithBodyAndResponse() {
		given()
				.accept(ContentType.JSON)
			.when()
				.get("/ittest/route/withBodyAndResponse")
			.then()
				.statusCode(HttpStatus.CREATED.code())
				.body("title", equalTo("My Feed Title"))
				.body("description", equalTo("My Feed Description"))
				.body("link", equalTo("http://localhost:8080"));
	}

}
