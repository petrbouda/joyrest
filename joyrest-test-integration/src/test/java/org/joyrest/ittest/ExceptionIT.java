package org.joyrest.ittest;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class ExceptionIT extends AbstractRestIntegrationTest {

    @Test
    public void handle_rest_exception() {
        given()
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/exception/badRequest")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.code());
    }

    @Test
    public void handle_exception_with_body() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/exception/numberFormat")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.BAD_REQUEST.code())
            .body("reason", equalTo("NumberFormatException"))
            .body("description", equalTo("Bad number format exception"));

    }

    /**
     * HTML content is required but there is no Writer for Bean which is return from Exception Handler to marshaller it to HTML
     * (UnknownWriter is just for Object.class not for given Bean)
     **/
    @Test
    public void handle_exception_with_body_unknown_writer() {
        given()
            .accept(ContentType.HTML)
            .contentType(ContentType.ANY)
            .when()
            .get("/ittest/exception/unknownWriter")
            .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.code());
    }

    @Test
    public void handle_parent_exception() {
        given()
            .contentType(ContentType.ANY)
            .accept(ContentType.JSON)
            .when()
            .post("/ittest/exception/parent")
            .then()
            .statusCode(HttpStatus.PRECONDITION_FAILED.code())
            .header("x-special-header", equalTo("SPECIAL"));
    }

    @Test
    public void handle_parent_exception_with_body() {
        given()
            .contentType(ContentType.ANY)
            .accept(ContentType.JSON)
            .when()
            .post("/ittest/exception/parentWithBody")
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.BAD_REQUEST.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void handle_exception_special_xml_writer() {
        given()
            .contentType(ContentType.ANY)
            .accept(ContentType.XML)
            .when()
            .post("/ittest/exception/onlySpecialWriter")
            .then()
            .contentType(ContentType.XML)
            .statusCode(HttpStatus.BAD_REQUEST.code())
            .body("contact.firstname", equalTo("Petr"))
            .body("contact.lastname", equalTo("Bouda"));
    }
}