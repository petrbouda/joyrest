package org.joyrest.context.helper;

import org.joyrest.aspect.Interceptor;
import org.joyrest.context.helper.aspects.FirstInterceptor;
import org.joyrest.context.helper.aspects.SecondInterceptor;
import org.joyrest.context.helper.aspects.ThirdInterceptor;
import org.joyrest.context.helper.transformer.FirstReader;
import org.joyrest.context.helper.transformer.GeneralReader;
import org.joyrest.context.helper.transformer.SecondReader;
import org.joyrest.model.http.MediaType;
import org.joyrest.transform.Reader;
import org.junit.Test;

import java.util.*;

import static org.joyrest.context.helper.ConfigurationHelper.createTransformers;
import static org.joyrest.context.helper.ConfigurationHelper.sort;
import static org.junit.Assert.*;

public class ConfigurationHelperTest {
	
	@Test
	public void testSort() throws Exception {
		List<Interceptor> interceptors = Arrays.asList(new FirstInterceptor(), new ThirdInterceptor(), new SecondInterceptor());
		List<Interceptor> sorted = new ArrayList<>(sort(interceptors));

		assertEquals(FirstInterceptor.ORDER, sorted.get(0).getOrder());
		assertEquals(SecondInterceptor.ORDER, sorted.get(1).getOrder());
		assertEquals(ThirdInterceptor.ORDER, sorted.get(2).getOrder());
	}

	@Test
	public void testCreateTransformers() throws Exception {
		List<Reader> readers = Arrays.asList(new FirstReader(), new GeneralReader(), new SecondReader(), new GeneralReader());
		Map<Boolean, List<Reader>> transformers = createTransformers(readers);

		assertEquals(2, transformers.get(Boolean.TRUE).size());
		assertEquals(2, transformers.get(Boolean.FALSE).size());

		List<Reader> generalReaders = transformers.get(Boolean.TRUE);
		assertEquals(MediaType.of("reader/GENERAL"), generalReaders.get(0).getMediaType());
		assertEquals(MediaType.of("reader/GENERAL"), generalReaders.get(1).getMediaType());

		List<Reader> commonReaders = transformers.get(Boolean.FALSE);
		assertEquals(MediaType.of("reader/FIRST"), commonReaders.get(0).getMediaType());
		assertEquals(MediaType.of("reader/SECOND"), commonReaders.get(1).getMediaType());
	}
}