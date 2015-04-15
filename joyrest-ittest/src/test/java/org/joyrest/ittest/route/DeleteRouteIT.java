package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class DeleteRouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRouteDelete_NoPathWithBody() {
		given()
			.when().delete("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteDelete_WithPathNoBody() {
		given()
			.when().delete("/ittest/route/withoutBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteDelete_WithPathWithBody() {
		given()
			.body("id-feed-entry")
			.contentType(ContentType.TEXT)
			.when().delete("/ittest/route/withBody")
			.then().statusCode(HttpStatus.OK.code());
	}

}
