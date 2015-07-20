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
