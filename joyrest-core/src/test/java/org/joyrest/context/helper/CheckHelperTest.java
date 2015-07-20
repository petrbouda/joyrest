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
package org.joyrest.context.helper;

import java.util.Arrays;
import java.util.List;

import org.joyrest.context.helper.aspects.DuplicatedInterceptor;
import org.joyrest.context.helper.aspects.FirstInterceptor;
import org.joyrest.context.helper.aspects.SecondInterceptor;
import org.joyrest.context.helper.aspects.ThirdInterceptor;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.interceptor.Interceptor;
import org.junit.Test;
import static org.joyrest.context.helper.CheckHelper.orderDuplicationCheck;
import static org.junit.Assert.fail;

public class CheckHelperTest {

    @Test
    public void with_correct_orders() {
        List<Interceptor> interceptors = Arrays.asList(new FirstInterceptor(), new SecondInterceptor(), new ThirdInterceptor());
        try {
            orderDuplicationCheck(interceptors);
        } catch (InvalidConfigurationException e) {
            fail("There is some problem with order duplications");
        }
    }

    @Test(expected = InvalidConfigurationException.class)
    public void with_duplication_exception() {
        List<Interceptor> interceptors = Arrays
            .asList(new FirstInterceptor(), new SecondInterceptor(), new DuplicatedInterceptor());
        orderDuplicationCheck(interceptors);
    }
}