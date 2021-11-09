package com.github.MehrabRahman.nasa;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringConfig {
    @Bean
    public Connection pgConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nasa", "nasa", "nasa");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Bean
    public ObjectMapper jsonMapper() {
        return new ObjectMapper();
    }

    @Bean
	public Tomcat server() {
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

		return server;
	}
}
