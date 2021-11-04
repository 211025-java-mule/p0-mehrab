package com.github.MehrabRahman.nasa;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nasa {
	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(Nasa.class.getName());
		ApplicationContext applicationContext = new ApplicationContext(args);
		Properties props = applicationContext.getProps();
		NasaService nasaService = applicationContext.getNasaService();

		Apod output = nasaService.getApod();
		
		//Console STDOUT
		System.out.println(output);

		//File
		File outputFile = new File("output.txt");
		try(FileWriter outpuFileWriter = new FileWriter(outputFile, true);	) {
			outpuFileWriter.write(output.toString() + "\n");
		} catch (IOException e) {
			log.error("Output file error");
		}

		//DB
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nasa", "nasa", "nasa");
			PreparedStatement statement = conn.prepareStatement("insert into apod(copyright, day, explanation, media_type, service_version, title, url, hdurl) values (?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, output.copyright);
			statement.setDate(2, Date.valueOf(output.date));
			statement.setString(3, output.explanation);
			statement.setString(4, output.media_type);
			statement.setString(5, output.service_version);
			statement.setString(6, output.title);
			statement.setString(7, output.url);
			statement.setString(8, output.hdurl);
			statement.executeUpdate();
			statement.close();
			statement = conn.prepareStatement("select * from apod");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				System.out.println("SQL: " + rs.getString("title"));
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void runServer() {
		Tomcat server = new Tomcat();
		server.setBaseDir(System.getProperty("java.io.tmpdir"));
		server.setPort(8080);
		server.getConnector();
		server.addContext("", null);
		server.addServlet("", "defaultServlet", new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				String fileName = req.getPathInfo().replaceFirst("/", "");
				InputStream file = getClass().getClassLoader().getResourceAsStream(fileName);
				String mimeType = getServletContext().getMimeType(fileName);
				resp.setContentType(mimeType);
				IOUtils.copy(file, resp.getOutputStream());
			}
		}).addMapping("/*");

		server.addServlet("", "helloServlet", new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				String name = req.getParameter("name");
				if (name == null)
						name = "Tomcat";
				resp.getWriter().println("<h1>Hello, " + name + "!</h1>");
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

