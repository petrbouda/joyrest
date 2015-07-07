package org.joyrest.ittest.route;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class DeleteRouteIT extends AbstractRestIntegrationTest {

    @Test
    public void delete_route_no_path() {
        given()
            .when().delete("/ittest/route")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void delete_path_with_path() {
        given()
            .when().delete("/ittest/route/withoutBody")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void delete_route_with_path_with_body() {
        given()
            .body("id-feed-entry")
            .contentType(ContentType.TEXT)
            .when().delete("/ittest/route/withBody")
            .then().statusCode(HttpStatus.OK.code());
    }

}
