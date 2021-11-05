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
import java.util.ArrayList;
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
	public static void main(String[] args) throws SQLException {
		Logger log = LoggerFactory.getLogger(Nasa.class.getName());
		ApplicationContext applicationContext = new ApplicationContext(args);
		Properties props = applicationContext.getProps();
		NasaService nasaService = applicationContext.getNasaService();
		ArrayList<ApodRepository> repositories = applicationContext.getRepositories();

		Apod output = nasaService.getApod();
		
		//UI - Console STDOUT
		System.out.println(output);

		//UI - HTTP Server

		// Sinks
		for(ApodRepository repository : repositories) {
			repository.create(output);
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

