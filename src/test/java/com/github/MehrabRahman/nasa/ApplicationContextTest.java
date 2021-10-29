package com.github.MehrabRahman.nasa;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void testProps() {
        String[] args = {"-t", "--api=apod"};
        ApplicationContext testContext = new ApplicationContext(args);
        Properties props = testContext.getProps();
        String expected = "true";
        String actual = props.getProperty("title");
        assertEquals(expected, actual);
    }
}
