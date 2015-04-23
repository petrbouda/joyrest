package org.joyrest.ittest.route;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.joyrest.ittest.setup.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import com.jayway.restassured.http.ContentType;

public class GetRouteIT extends AbstractBasicIT {

	@Test
	public void get_route_no_path() {
		given()
			.when()
			.get("/ittest/route")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void get_route_with_path() {
		given()
			.when()
			.get("/ittest/route/withPath")
			.then()
			.statusCode(HttpStatus.OK.code());
	}

	@Test
	public void get_route_with_path_with_response() {
		given()
			.accept(ContentType.JSON)
			.when()
			.get("/ittest/route/withResponse")
			.then()
			.statusCode(HttpStatus.CREATED.code())
			.body("title", equalTo("My Feed Title"))
			.body("description", equalTo("My Feed Description"))
			.body("link", equalTo("http://localhost:8080"));
	}

}
