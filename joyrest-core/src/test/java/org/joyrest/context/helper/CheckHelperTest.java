package org.joyrest.context.helper;

import org.joyrest.aspect.Interceptor;
import org.joyrest.context.helper.aspects.*;
import org.joyrest.context.helper.aspects.DuplicatedInterceptor;
import org.joyrest.context.helper.aspects.FirstInterceptor;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.joyrest.context.helper.CheckHelper.orderDuplicationCheck;
import static org.junit.Assert.*;

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
		List<Interceptor> interceptors = Arrays.asList(new FirstInterceptor(), new SecondInterceptor(), new DuplicatedInterceptor());
		orderDuplicationCheck(interceptors);
	}
}