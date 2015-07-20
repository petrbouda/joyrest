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
package org.joyrest.ittest.route;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class PutRouteIT extends AbstractRestIntegrationTest {

    @Test
    public void put_route_no_path_no_body() {
        given()
            .when()
            .put("/ittest/route")
            .then()
            .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void put_route_with_path_with_body() {
        given()
            .body(feedEntity)
            .contentType(ContentType.JSON)
            .when()
            .put("/ittest/route/withBody")
            .then()
            .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void put_route_with_path_with_response() {
        given()
            .accept(ContentType.JSON)
            .when()
            .put("/ittest/route/withResponse")
            .then()
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void put_route_with_path_with_request_and_response() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(feedEntity)
            .when()
            .put("/ittest/route/withBoth")
            .then()
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }

}
