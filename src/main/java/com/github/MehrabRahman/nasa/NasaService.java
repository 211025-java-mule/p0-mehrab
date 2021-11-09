package com.github.MehrabRahman.nasa;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
public class NasaService {
    private ObjectMapper mapper;

    public NasaService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Apod getApod() {
        URL url = null;
		try {
			url = new URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
		} catch (MalformedURLException e) {
			System.err.println("404 not found");
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("400 Error connecting");
		}
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", "java");
		InputStream response = null;
		try {
			response = connection.getInputStream();
		} catch (IOException e) {
			System.err.println("Could not open response");
		}
		String body = null;
		try {
			body = new String(response.readAllBytes());
		} catch (IOException e) {
			System.err.println("Could not read response");
		}

		//UI
		Apod output = new Apod();
		try {
			output = mapper.readValue(body, Apod.class);
		} catch (JsonProcessingException e) {
			System.err.println("Could not parse response");
			System.err.println(e.getMessage());
		}

        return output;
    }
}
