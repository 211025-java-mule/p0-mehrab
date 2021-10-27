import java.net.URL;
import java.util.Properties;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Nasa {
	public static void main(String[] args) {
		Properties props = new Properties();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case "-t":
						props.setProperty("title", "true");
						break;
					case "--api":
						i++;
						props.setProperty("api", args[i]);
						break;
					default:
						System.err.println("Unknown argument: " + args[i]);
						break;
				}
			}
		} else {
			try {
				props.load(new FileReader("application.properties"));
			} catch (IOException e) {
				System.err.println("Configuration file not found");
			}
		}
		props.forEach((k, v) -> System.out.println(k + " = " + v));
		ObjectMapper mapper = new ObjectMapper();

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

		Apod output = new Apod();
		try {
			output = mapper.readValue(body, Apod.class);
		} catch (JsonProcessingException e) {
			System.err.println("Could not parse response");
			System.err.println(e.getMessage());
		}
		System.out.println(output);
	}
}

