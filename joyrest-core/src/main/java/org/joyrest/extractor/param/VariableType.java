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
package org.joyrest.extractor.param;

import java.util.Optional;

/**
 * Abstract type of a part in the path.
 *
 * @param <T> type of the variable
 * @see AbstractVariable
 * @see IntegerVariable
 * @see LongVariable
 * @see StringVariable
 * @author pbouda
 */
public interface VariableType<T> {

    /**
     * Method parses and casts from the string value to a concrete value defined in a child class.
     *
     * @param value string representation of the concrete value
     * @return parses optional value
     */
    Optional<T> valueOf(String value);

    /**
     * Name of the path variable
     *
     * @return path variable name
     */
    String getName();

    /**
     * Figures out whether is value assignable to the class which is defined in a child class.
     *
     * @param value value in string representation
     * @return returns {@code true} if the value is assignable to a class defined in a child class
     */
    boolean isAssignableFromString(String value);

}
