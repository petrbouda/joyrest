package org.joyrest.ittest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class RouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testRoutePost_NoPathWithBody() {
		given().body(feedEntity)
			.when()
			.contentType(ContentType.JSON)
			.post("/services/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithPathWithBody() {
		given().body(feedEntity)
			.when().post("/ittest/route/withBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithPathWithSpecifiedBody() {
		given().body(feedEntity)
			.when().post("/ittest/route/withSpecifiedBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePost_WithPathNoBody() {
		given()
			.when().post("/ittest/route/withoutBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	/*
	* GET ROUTES
	**/

	@Test
	public void testRouteGet_NoPathWithBody() {
		given().body(feedEntity)
			.when().get("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteGet_WithPathWithBody() {
		given().body(feedEntity)
			.when().get("/ittest/route/withBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteGet_WithPathWithSpecifiedBody() {
		given().body(feedEntity)
			.when().get("/ittest/route/withSpecifiedBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteGet_WithPathNoBody() {
		given()
			.when().get("/ittest/route/withoutBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	/*
	* PUT ROUTES
	**/

	@Test
	public void testRoutePut_NoPathWithBody() {
		given().body(feedEntity)
			.when().put("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePut_WithPathWithBody() {
		given().body(feedEntity)
			.when().put("/ittest/route/withBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePut_WithPathWithSpecifiedBody() {
		given().body(feedEntity)
			.when().put("/ittest/route/withSpecifiedBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRoutePut_WithPathNoBody() {
		given()
			.when().put("/ittest/route/withoutBody")
			.then().statusCode(HttpStatus.OK.code());
	}

	/*
	* DELETE ROUTES
	**/

	@Test
	public void testRouteDelete_NoPathWithBody() {
		given().body(feedEntity)
			.when().delete("/ittest/route")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testRouteDelete_WithPathNoBody() {
		given()
			.when().delete("/ittest/route/withoutBody")
			.then().statusCode(HttpStatus.OK.code());
	}

}
