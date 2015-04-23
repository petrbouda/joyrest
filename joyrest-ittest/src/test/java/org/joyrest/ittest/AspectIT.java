package org.joyrest.ittest;

import static com.jayway.restassured.RestAssured.given;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;

@Ignore
public class AspectIT extends AbstractRestIntegrationTest {

	@Test
	public void testRoutePost_NoPathWithBody() {
		given().body(feedEntity)
			.when()
			.contentType(ContentType.JSON)
			.post("/ittest/aspect")
			.then().statusCode(HttpStatus.OK.code());
	}

}
