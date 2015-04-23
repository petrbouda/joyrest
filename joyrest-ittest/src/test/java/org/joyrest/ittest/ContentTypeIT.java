package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.setup.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

public class ContentTypeIT extends AbstractBasicIT {

	@Test
	public void content_type_wildcard() {
		given()
			.contentType(ContentType.ANY)
			.when()
			.post("/ittest/content-type/wildcard")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void content_type_json() {
		given()
			.contentType(ContentType.JSON)
			.body(feedEntity)
			.when()
			.post("/ittest/content-type/app-json")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void multiple_content_type_json() {
		given()
			.contentType(ContentType.JSON)
			.body(feedEntity)
			.when()
			.post("/ittest/content-type/app-json-xml-text")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

	@Test
	public void multiple_content_type_text() {
		given()
			.contentType(ContentType.TEXT)
			.body("PlainTextBody")
			.when()
			.post("/ittest/content-type/app-json-text")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}
}
