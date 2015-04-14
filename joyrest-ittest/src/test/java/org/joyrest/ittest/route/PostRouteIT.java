package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class PostRouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRoutePost_NoPathWithBody() {
		given().body(feedEntity)
			.when()
			.contentType(ContentType.JSON)
			.post("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithPath() {
		given()
			.contentType(ContentType.ANY)
			.when().post("/ittest/route/withPath")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithPathWithConsume() {
		given().body(feedEntity)
			.contentType(ContentType.JSON)
			.when().post("/ittest/route/withPathWithConsume")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithBodyAndResponse() {
		given()
			.body(feedEntity)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when().post("/ittest/route/withBodyAndResponse")
			.then().statusCode(HttpStatus.CREATED.code());
	}

}
