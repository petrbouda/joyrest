package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

public class PostRouteIT extends AbstractRestIntegrationTest {

	@Test
	public void post_route_no_path_with_body() {
		given().body(feedEntity)
			.when()
			.contentType(ContentType.JSON)
			.post("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void post_route_with_path() {
		given()
			.contentType(ContentType.ANY)
			.when().post("/ittest/route/withPath")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void post_route_with_path_with_consume() {
		given().body(feedEntity)
			.contentType(ContentType.JSON)
			.when().post("/ittest/route/withPathWithConsume")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void post_route_with_body_with_response() {
		given()
			.body(feedEntity)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when().post("/ittest/route/withBodyAndResponse")
			.then().statusCode(HttpStatus.CREATED.code());
	}

}
