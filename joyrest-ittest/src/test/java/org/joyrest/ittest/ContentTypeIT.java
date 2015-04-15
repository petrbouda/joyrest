package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.model.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class ContentTypeIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testContentType_Wildcard() {
		given()
			.contentType(ContentType.ANY)
			.when()
			.post("/ittest/content-type/wildcard")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testContentType_Json() {
		given()
			.contentType(ContentType.JSON)
			.body(feedEntity)
			.when()
			.post("/ittest/content-type/app-json")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testContentType_JsonXmlText() {
		given()
			.contentType(ContentType.JSON)
			.body(feedEntity)
			.when()
			.post("/ittest/content-type/app-json-xml-text")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void testContentType_JsonText_string() {
		given()
			.contentType(ContentType.TEXT)
			.body("PlainTextBody")
			.when()
			.post("/ittest/content-type/app-json-text")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}
}
