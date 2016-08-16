package com.softserverinc.edu.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DBConfigTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(DBConfigTest.class);

    @Test
    public void testDataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        LOGGER.info("Simple DBConfigTest");
        Assert.assertNotNull(dataSource);
    }

}