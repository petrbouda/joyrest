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
package org.joyrest.examples.combiner.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A simple ID generator based on the sequence generating.
 *
 * New IDs are generated in order and every returned ID is unique for the instance of generater in which was created.
 *
 * */
public final class SequenceIdGenerator implements IdGenerator {

    /* Sequence holder */
    private final AtomicLong sequence = new AtomicLong(1);

    /**
     * Generated unique ID in sequence for the given instance
     *
     * @return generated ID
     * */
    public String getId() {
        return String.valueOf(sequence.getAndIncrement());
    }

}
