import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nasa {
	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(Nasa.class.getName());
		Properties props = new Properties();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case "-s":
						runServer();
					case "-t":
						props.setProperty("title", "true");
						break;
					case "--api":
						i++;
						props.setProperty("api", args[i]);
						break;
					default:
						log.debug("Unknown argument");
						break;
				}
			}
		} else {
			try {
				props.load(Nasa.class.getClassLoader().getResourceAsStream("application.properties"));
				
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
		File outputFile = new File("output.txt");
		try(FileWriter outpuFileWriter = new FileWriter(outputFile, true);	) {
			outpuFileWriter.write(output.toString() + "\n");
		} catch (IOException e) {
			log.error("Output file error");
		}
	}

	private static void runServer() {
		Tomcat server = new Tomcat();
		server.setBaseDir(System.getProperty("java.io.tmpdir"));
		server.setPort(8081);
		server.getConnector();
		server.addContext("", null);
		server.addServlet("", "helloServlet", new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				resp.getWriter().println("Hello from Tomcat!");
			}
		}).addMapping("/hello");
		try {
			server.start();
			server.getServer().await();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

