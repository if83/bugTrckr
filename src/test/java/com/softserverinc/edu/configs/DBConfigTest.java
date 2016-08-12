package com.softserverinc.edu.configs;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DBConfigTest {
    @Test
    public void testDataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Assert.assertNotNull(dataSource);
    }

}