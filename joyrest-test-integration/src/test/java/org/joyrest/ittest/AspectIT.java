package org.joyrest.ittest;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

@Ignore
public class AspectIT extends AbstractRestIntegrationTest {

    @Test
    public void aspects_order() {
        given()
            .when()
            .contentType(ContentType.ANY)
            .get("/ittest/interceptor")
            .then()
            .header("result", equalTo("success"))
            .statusCode(HttpStatus.OK.code());
    }

}
