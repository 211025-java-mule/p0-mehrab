package com.github.MehrabRahman.nasa;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

public class NasaServiceTest {
    ObjectMapper mapper;
    NasaService nasaService;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        nasaService = new NasaService(mapper);
    }

    @Test
    public void getApodTest() {
        Apod testApod = nasaService.getApod();
        assertNotNull(testApod.date);
    }
}
