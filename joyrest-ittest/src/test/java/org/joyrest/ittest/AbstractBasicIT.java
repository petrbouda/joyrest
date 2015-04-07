package org.joyrest.ittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joyrest.ittest.routes.Application;
import org.joyrest.ittest.routes.ApplicationConfig;
import org.joyrest.ittest.routes.entity.FeedEntry;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

<<<<<<< HEAD
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
=======
/**
 * TODO Udelat testy na Spring Boot !!
 * */
>>>>>>> bca5483... refactoring and TODO spring boot
public abstract class AbstractBasicIT {

    @Value("${local.server.port}")
    int port;

    public static final ObjectMapper mapper = new ObjectMapper();

    public static String feedEntity = null;

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
