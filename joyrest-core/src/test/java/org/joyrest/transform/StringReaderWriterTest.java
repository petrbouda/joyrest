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
package org.joyrest.transform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.joyrest.stubs.RequestStub;
import org.joyrest.stubs.ResponseStub;
import org.junit.Assert;
import org.junit.Test;
import static org.joyrest.routing.entity.RequestType.Req;

public class StringReaderWriterTest {

    private final StringReaderWriter testedClass = new StringReaderWriter();

    @Test
    public void read_from_success() {
        String entity = "Well Done!";

        RequestStub request = new RequestStub();
        request.setInputStream(new ByteArrayInputStream(entity.getBytes(StandardCharsets.UTF_8)));

        String result = testedClass.readFrom(request, Req(String.class));
        Assert.assertEquals(entity, result);
    }

    @Test
    public void write_to_blank() {
        RequestStub request = new RequestStub();
        ResponseStub response = new ResponseStub();
        response.setOutputStream(new ByteArrayOutputStream());

        testedClass.writeTo(response, request);

        ByteArrayOutputStream outputStream = (ByteArrayOutputStream) response.getOutputStream();
        Assert.assertEquals("", new String(outputStream.toByteArray()));
    }

    @Test
    public void write_to_success() {
        String entity = "Well Done!";

        RequestStub request = new RequestStub();
        ResponseStub response = new ResponseStub();
        response.entity(entity);
        response.setOutputStream(new ByteArrayOutputStream());

        testedClass.writeTo(response, request);

        ByteArrayOutputStream outputStream = (ByteArrayOutputStream) response.getOutputStream();
        Assert.assertEquals(entity, new String(outputStream.toByteArray()));
    }

}