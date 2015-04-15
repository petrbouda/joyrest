package org.joyrest.ittest;

import com.jayway.restassured.RestAssured;
import org.junit.Before;

public class ContentPathIT extends AbstractBasicIT {

    @Before
    public void setUp() {
        RestAssured.port = port;
    }


}
