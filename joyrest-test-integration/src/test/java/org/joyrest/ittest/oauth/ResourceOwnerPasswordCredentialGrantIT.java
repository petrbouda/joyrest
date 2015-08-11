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
package org.joyrest.ittest.oauth;

import java.net.URLEncoder;

import org.joyrest.ittest.setup.AbstractRestIntegrationTest;
import org.joyrest.model.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class ResourceOwnerPasswordCredentialGrantIT extends AbstractRestIntegrationTest {

    private static final String BODY = "grant_type=password&username=roy&password=spring";

    @Test
    public void get_token() {
        given()
            .contentType(ContentType.URLENC)
            .when()
            .body(BODY)
            .post("/oauth/token")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void accept_wildcard_against_json() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(feedEntity)
            .when()
            .post("/ittest/accept/wildcard")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void accept_json_with_wildcard() {
        given()
            .contentType(ContentType.ANY)
            .accept(ContentType.ANY)
            .when()
            .get("/ittest/accept")
            .then()
            .statusCode(HttpStatus.OK.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }

    @Test
    public void accept_json() {
        given()
            .contentType(ContentType.ANY)
            .accept(ContentType.JSON)
            .when()
            .post("/ittest/accept/app-json")
            .then()
            .statusCode(HttpStatus.OK.code());
    }

    @Test
    public void multiple_accept_json() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(feedEntity)
            .when()
            .post("/ittest/accept/app-json-xml")
            .then()
            .statusCode(HttpStatus.CREATED.code())
            .body("title", equalTo("My Feed Title"))
            .body("description", equalTo("My Feed Description"))
            .body("link", equalTo("http://localhost:8080"));
    }
}
