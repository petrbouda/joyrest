package org.joyrest.ittest;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class ParamsIT extends AbstractRestIntegrationTest {

    @Test
    public void params_id() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/params/1")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("ID path param = 1"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void params_id_integer() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/params/int/2")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("ID path param = 2"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void multiple_params() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/params/int/1/next/2")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("ID path params = 1 and 2"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void param_type_validation_error() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/params/int/d")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.BAD_REQUEST.code())
            .body("description", equalTo("For input string: \"d\""))
            .body("reason", equalTo("NumberFormatException"));
    }

}