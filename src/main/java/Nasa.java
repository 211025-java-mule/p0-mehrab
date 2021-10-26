import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Nasa {
	public static void main(String[] args) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", "java");
		InputStream response = connection.getInputStream();
		String body = new String(response.readAllBytes());

		Apod output = mapper.readValue(body, Apod.class);
		System.out.println(output);
	}
}

