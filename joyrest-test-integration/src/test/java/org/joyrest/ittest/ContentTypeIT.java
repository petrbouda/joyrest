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

public class ContentTypeIT extends AbstractRestIntegrationTest {

    @Test
    public void content_type_wildcard() {
        given()
            .contentType(ContentType.ANY)
            .when()
            .post("/ittest/content-type/wildcard")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void content_type_wildcard_against_json() {
        given()
            .contentType(ContentType.JSON)
            .body(feedEntity)
            .when()
            .post("/ittest/content-type/wildcard")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void content_type_json() {
        given()
            .contentType(ContentType.JSON)
            .body(feedEntity)
            .when()
            .post("/ittest/content-type/app-json")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void multiple_content_type_json() {
        given()
            .contentType(ContentType.JSON)
            .body(feedEntity)
            .when()
            .post("/ittest/content-type/app-json-xml-text")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void multiple_content_type_text() {
        given()
            .contentType(ContentType.TEXT)
            .body("PlainTextBody")
            .when()
            .post("/ittest/content-type/app-json-text")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.code());
    }

    @Test
    public void multiple_content_type_text_$_accept_matched_usage() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.TEXT)
            .body(feedEntity)
            .when()
            .post("/ittest/content-type/app-json-text-accept-matched")
            .then()
            .statusCode(HttpStatus.CREATED.code())
            .body(equalTo("Well Done!!"));
    }
}
