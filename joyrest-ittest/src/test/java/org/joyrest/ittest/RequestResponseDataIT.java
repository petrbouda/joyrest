package org.joyrest.ittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.MediaType;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class RequestResponseDataIT extends AbstractBasicIT {

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void postRouteTest() throws JsonProcessingException {
		FeedEntry f = new FeedEntry();
		f.setDescription("Holaaa");

		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(f);

		Response resp = given()
			.contentType(MediaType.JSON.getType())
			.body(string)
			.when()
			.post("feeds");

		String json = resp.asString();
		System.out.println("");
	}

}