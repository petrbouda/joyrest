package org.joyrest.ittest.path;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class Path1RouteIT extends AbstractRestIntegrationTest {

    @Test
    public void blank_path() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void path_with_no_slash() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path/path0")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void path_ends_with_slash() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path/path1")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void path_starts_ends_with_slash() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path/path2")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void path_starts_with_slash() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path/path3")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void multiple_path_with_slash() {
        given().contentType(ContentType.ANY)
            .when().post("/ittest/path/path1/path2")
            .then().statusCode(HttpStatus.NO_CONTENT.code());
    }
}
