package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.joyrest.ittest.setup.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import com.jayway.restassured.http.ContentType;

public class PutRouteIT extends AbstractBasicIT {

	@Test
	public void put_route_no_path_no_body() {
		given()
			.when()
			.put("/ittest/route")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void put_route_with_path_with_body() {
		given()
			.body(feedEntity)
			.contentType(ContentType.JSON)
			.when()
			.put("/ittest/route/withBody")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void put_route_with_path_with_response() {
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
	public void put_route_with_path_with_request_and_response() {
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
