package com.github.MehrabRahman.nasa;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Nasa {
	public static void main(String[] args) throws SQLException {
		Logger log = LoggerFactory.getLogger(Nasa.class.getName());
		// ApplicationContext applicationContext = new ApplicationContext(args);
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		// applicationContext.register(ApodFileRepository.class, ApodPostgresRepository.class, NasaService.class, SpringConfig.class);
		// applicationContext.scan("com.github.MehrabRahman.nasa");
		// applicationContext.refresh();
		// Properties props = applicationContext.getProps();
		// NasaService nasaService = applicationContext.getNasaService();
		NasaService nasaService = applicationContext.getBean(NasaService.class);
		// ArrayList<ApodRepository> repositories = applicationContext.getRepositories();
		ArrayList<ApodRepository> repositories = new ArrayList<>();
		repositories.add(applicationContext.getBean(ApodFileRepository.class));
		repositories.add(applicationContext.getBean(ApodPostgresRepository.class));
		Tomcat server = applicationContext.getBean(Tomcat.class);

		Apod output = nasaService.getApod();
		
		//UI - Console STDOUT
		System.out.println(output);

		//UI - HTTP Server
		try {
			server.start();
			server.getServer().await();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Sinks
		for(ApodRepository repository : repositories) {
			repository.create(output);
		}
		applicationContext.close();
	}
}

