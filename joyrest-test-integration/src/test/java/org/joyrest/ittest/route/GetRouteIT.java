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

public class GetRouteIT extends AbstractRestIntegrationTest {

    @Test
    public void get_route_no_path() {
        given()
            .when()
            .get("/ittest/route")
            .then()
            .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void get_route_with_path() {
        given()
            .when()
            .get("/ittest/route/withPath")
            .then()
            .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void get_route_with_path_with_response() {
        given()
            .accept(ContentType.JSON)
            .when()
            .get("/ittest/route/withResponse")
            .then()
            .statusCode(HttpStatus.CREATED.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }

}
