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

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class PostRouteIT extends AbstractRestIntegrationTest {

    @Test
    public void post_route_no_path_with_body() {
        given().body(feedEntity)
            .when()
            .contentType(ContentType.JSON)
            .post("/ittest/route")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void post_route_with_path() {
        given()
            .contentType(ContentType.ANY)
            .when().post("/ittest/route/withPath")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void post_route_with_path_with_consume() {
        given().body(feedEntity)
            .contentType(ContentType.JSON)
            .when().post("/ittest/route/withPathWithConsume")
            .then().statusCode(HttpStatus.OK.code());
    }

    @Test
    public void post_route_with_body_with_response() {
        given()
            .body(feedEntity)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when().post("/ittest/route/withBodyAndResponse")
            .then().statusCode(HttpStatus.CREATED.code());
    }

}
