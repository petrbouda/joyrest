package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

public class RequestResponseDataIT extends AbstractRestIntegrationTest {

	@Test
	public void validate_request_response_objects() {
		given()
			.contentType(ContentType.JSON)
			.body(feedEntity)
			.when()
			.post("feeds")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.code());
	}

}