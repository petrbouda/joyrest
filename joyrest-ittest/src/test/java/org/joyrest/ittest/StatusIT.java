package org.joyrest.ittest;

import com.jayway.restassured.RestAssured;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class StatusIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testStatusGet200() {
		given()
			.when().get("/services/ittest/status/200")
			.then().statusCode(HttpStatus.OK.code());
	}

	@Test
	public void testStatusPost201() {
		given().body(feedEntity)
			.when().post("/services/ittest/status/201")
			.then().statusCode(HttpStatus.CREATED.code());
	}

	@Test
	public void testStatusPost204() {
		given()
			.when().post("/services/ittest/status/204")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testStatusGet301() {
		given()
			.when().get("/ittest/status/301")
			.then().statusCode(HttpStatus.MOVED_PERMANENTLY.code());
	}

	@Test
	public void testStatusGet302() {
		given()
			.when().get("/services/ittest/status/302")
			.then().statusCode(HttpStatus.FOUND.code());
	}

	@Test
	public void testStatusPost400() {
		given().body(feedEntity)
			.when().post("/services/ittest/status/400")
			.then().statusCode(HttpStatus.BAD_REQUEST.code());
	}

	@Test
	public void testStatusGet403() {
		given()
			.when().get("/services/ittest/status/403")
			.then().statusCode(HttpStatus.FORBIDDEN.code());
	}

	@Test
	public void testStatusGet404() {
		given()
			.when().get("/services/ittest/status/404")
			.then().statusCode(HttpStatus.NOT_FOUND.code());
	}

	@Test
	public void testStatusGet500() {
		given()
			.when().get("/services/ittest/status/500")
			.then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.code());
	}

	@Test
	public void testStatusGet501() {
		given()
			.when().get("/services/ittest/status/501")
			.then().statusCode(HttpStatus.NOT_IMPLEMENTED.code());
	}

}
