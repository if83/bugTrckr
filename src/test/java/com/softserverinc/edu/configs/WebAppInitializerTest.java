package com.softserverinc.edu.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class WebAppInitializerTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebAppInitializerTest.class);

    @Test
    public void testOnStartup() throws Exception {
        LOGGER.info("Simple WebAppInitializerTest");
    }

}