package org.joyrest.ittest;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class StatusIT extends AbstractRestIntegrationTest {

    @Test
    public void status_get_200() {
        given()
            .when().get("/ittest/status/200")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void status_post_201() {
        given().body(feedEntity)
            .contentType(ContentType.JSON)
            .when().post("/ittest/status/201")
            .then().statusCode(HttpStatus.CREATED.code());
    }

    @Test
    public void status_post_204() {
        given()
            .contentType(ContentType.ANY)
            .when().post("/ittest/status/204")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void status_get_302() {
        given()
            .when().get("/ittest/status/302")
            .then().statusCode(HttpStatus.FOUND.code());
    }

    @Test
    public void status_post_400() {
        given().body(feedEntity)
            .contentType(ContentType.JSON)
            .when().post("/ittest/status/400")
            .then().statusCode(HttpStatus.BAD_REQUEST.code());
    }

    @Test
    public void status_get_403() {
        given()
            .when().get("/ittest/status/403")
            .then().statusCode(HttpStatus.FORBIDDEN.code());
    }

    @Test
    public void status_get_404() {
        given()
            .when().get("/ittest/status/404")
            .then().statusCode(HttpStatus.NOT_FOUND.code());
    }

    @Test
    public void status_get_500() {
        given()
            .when().get("/ittest/status/500")
            .then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.code());
    }

    @Test
    public void status_get_501() {
        given()
            .when().get("/ittest/status/501")
            .then().statusCode(HttpStatus.NOT_IMPLEMENTED.code());
    }

}
