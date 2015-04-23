package org.joyrest.ittest.path;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.setup.AbstractBasicIT;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import com.jayway.restassured.http.ContentType;

public class Path3RouteIT extends AbstractBasicIT {

	@Test
	public void empty_path() {
		given()
			.contentType(ContentType.ANY)
			.when()
			.post("/ittest/path3")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

}
