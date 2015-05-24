package org.joyrest.ittest;

import com.jayway.restassured.http.ContentType;
import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AcceptIT extends AbstractRestIntegrationTest {

    @Test
    public void accept_wildcard() {
        given()
                .contentType(ContentType.ANY)
                .when()
                .post("/ittest/accept/wildcard")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void accept_wildcard_against_json() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(feedEntity)
                .when()
                .post("/ittest/accept/wildcard")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void accept_json_with_wildcard() {
        given()
                .contentType(ContentType.ANY)
                .accept(ContentType.ANY)
                .when()
                .get("/ittest/accept")
                .then()
                .statusCode(HttpStatus.OK.code())
                .body("title", equalTo("My Feed Title"))
                .body("description", equalTo("My Feed Description"))
                .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void accept_json() {
        given()
                .contentType(ContentType.ANY)
                .accept(ContentType.JSON)
                .when()
                .post("/ittest/accept/app-json")
                .then()
                .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void multiple_accept_json() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(feedEntity)
                .when()
                .post("/ittest/accept/app-json-xml")
                .then()
                .statusCode(HttpStatus.CREATED.code())
                .body("title", equalTo("My Feed Title"))
                .body("description", equalTo("My Feed Description"))
                .body("link", equalTo("http://localhost:8080"));
    }
}
