/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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