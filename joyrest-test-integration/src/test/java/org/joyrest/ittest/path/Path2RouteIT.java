package org.joyrest.ittest.path;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class Path2RouteIT extends AbstractRestIntegrationTest {

    @Test
    public void path_only_with_slash() {
        given()
            .contentType(ContentType.ANY)
            .when()
            .post("/ittest/path2")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

}
