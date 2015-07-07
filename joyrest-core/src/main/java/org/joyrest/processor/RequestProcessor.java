/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

/**
 * An interface of a processor which is able to generate a correct {@link Response} thank to information provided from the
 * {@link InternalRequest} object.
 *
 * @author pbouda
 */
@FunctionalInterface
public interface RequestProcessor {

    /**
     * Modify {@link InternalResponse response} on the base of the incoming {@link InternalRequest provider}
     * as a abstraction of any server data.
     *
     * @param request incoming model in the form of {@link InternalRequest}
     * @param response response in which is stored a processing result
     * @throws java.lang.Exception exception occurred during processing in joyrest framework
     */
    void process(InternalRequest<Object> request, InternalResponse<Object> response) throws Exception;

}
