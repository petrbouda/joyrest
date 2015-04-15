package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.joyrest.ittest.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class PutRouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRoutePut_NoPathNoBody() {
		given()
			.when()
			.put("/ittest/route")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePut_WithPathWithBody() {
		given()
			.body(feedEntity)
			.contentType(ContentType.JSON)
			.when()
			.put("/ittest/route/withBody")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePut_WithPathWithResponse() {
		given()
			.accept(ContentType.JSON)
			.when()
			.put("/ittest/route/withResponse")
			.then()
			.statusCode(HttpStatus.OK.code())
			.body("title", equalTo("My Feed Title"))
			.body("description", equalTo("My Feed Description"))
			.body("link", equalTo("http://localhost:8080"));
	}

	@Test
	public void testRoutePut_WithPathWithBoth() {
		given()
			.body(feedEntity)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when()
			.put("/ittest/route/withBoth")
			.then()
			.statusCode(HttpStatus.OK.code())
			.body("title", equalTo("My Feed Title"))
			.body("description", equalTo("My Feed Description"))
			.body("link", equalTo("http://localhost:8080"));
	}

}
