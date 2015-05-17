package org.joyrest.context.helper;

import org.joyrest.aspect.Aspect;
import org.joyrest.context.helper.aspects.FirstAspect;
import org.joyrest.context.helper.aspects.SecondAspect;
import org.joyrest.context.helper.aspects.ThirdAspect;
import org.joyrest.context.helper.transformer.FirstReader;
import org.joyrest.context.helper.transformer.GeneralReader;
import org.joyrest.context.helper.transformer.SecondReader;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.MediaType;
import org.joyrest.transform.Reader;
import org.junit.Test;

import java.util.*;

import static org.joyrest.context.helper.CheckHelper.orderDuplicationCheck;
import static org.joyrest.context.helper.ConfigurationHelper.createTransformers;
import static org.joyrest.context.helper.ConfigurationHelper.sort;
import static org.junit.Assert.*;

public class ConfigurationHelperTest {
	
	@Test
	public void testSort() throws Exception {
		List<Aspect> aspects = Arrays.asList(new FirstAspect(), new ThirdAspect(), new SecondAspect());
		List<Aspect> sorted = new ArrayList<>(sort(aspects));

		assertEquals(FirstAspect.ORDER, sorted.get(0).getOrder());
		assertEquals(SecondAspect.ORDER, sorted.get(1).getOrder());
		assertEquals(ThirdAspect.ORDER, sorted.get(2).getOrder());
	}

	@Test
	public void testCreateTransformers() throws Exception {
		List<Reader> readers = Arrays.asList(new FirstReader(), new GeneralReader(), new SecondReader(), new GeneralReader());
		Map<Boolean, List<Reader>> transformers = createTransformers(readers, Collections.emptyList());

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