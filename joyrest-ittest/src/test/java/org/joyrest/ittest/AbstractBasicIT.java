package org.joyrest.ittest;

import java.util.Date;

import org.joyrest.ittest.config.Application;
import org.joyrest.ittest.entity.FeedEntry;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public abstract class AbstractBasicIT {

	public static final ObjectMapper mapper = new ObjectMapper();
	public static String feedEntity = null;
	@Value("${local.server.port}")
	protected int port;

	@BeforeClass
	public static void initClass() throws JsonProcessingException {
		FeedEntry f = new FeedEntry();
		f.setLink("http://localhost:8080");
		f.setPublishDate(new Date());
		f.setTitle("My Feed Title");
		f.setDescription("My Feed Description");
		feedEntity = mapper.writeValueAsString(f);
	}

}
