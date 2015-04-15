package org.joyrest.ittest.path;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class Path1RouteIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testPath_Blank() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testPath_NoSlash() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path/path0")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testPath_SlashEnd() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path/path1")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testPath_SlashBoth() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path/path2")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testPath_SlashStart() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path/path3")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testPath_SlashMultiple() {
		given().contentType(ContentType.ANY)
			.when().post("/ittest/path/path1/path2")
			.then().statusCode(HttpStatus.NO_CONTENT.code());
	}
}
