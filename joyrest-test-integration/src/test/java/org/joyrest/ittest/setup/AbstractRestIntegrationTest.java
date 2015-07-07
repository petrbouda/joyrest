package org.joyrest.ittest.setup;

import java.util.Date;

import org.joyrest.ittest.ApplicationConfig;
import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.logging.JoyLogger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.IntegrationTestPropertiesListener;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@TestExecutionListeners(listeners = {
    IntegrationTestPropertiesListener.class,
    DependencyInjectionTestExecutionListener.class})
public abstract class AbstractRestIntegrationTest {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static String feedEntity = null;
    private static JoyLogger log = new JoyLogger(AbstractRestIntegrationTest.class);

    static {
        try {
            FeedEntry f = new FeedEntry();
            f.setLink("http://localhost:8080");
            f.setPublishDate(new Date());
            f.setTitle("My Feed Title");
            f.setDescription("My Feed Description");
            feedEntity = mapper.writeValueAsString(f);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred during parsing the testing FeedEntry entity.");
        }
    }

    @Rule
    public TestName name = new TestName();
    @Value("${local.server.port}")
    protected int port;

    @Before
    public void setUp() {
        RestAssured.port = port;

        log.debug(() ->
            "\n ------------------------------------------------------ \n " +
                "# Run test: " + name.getMethodName() +
                "\n ------------------------------------------------------");
    }

}
