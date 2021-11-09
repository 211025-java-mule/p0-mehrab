// package com.github.MehrabRahman.nasa;

// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Properties;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


// public class ApplicationContext {
//     private final static Logger log = LoggerFactory.getLogger(ApplicationContext.class.getName());
//     private Properties props;
//     private ObjectMapper mapper;
// 	private NasaService nasaService;
//     private ArrayList<ApodRepository> repositories = new ArrayList<>();
//     private ApodPostgresRepository apodRepository;
//     private ApodFileRepository apodFileRepository;
    
//     public ApplicationContext(String[] args) throws SQLException {
//         this.props = new Properties();
//         this.mapper = new ObjectMapper();
// 		this.nasaService = new NasaService(mapper);
//         Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nasa", "nasa", "nasa");		
//         this.apodRepository = new ApodPostgresRepository(conn);
//         this.apodFileRepository = new ApodFileRepository();
// 		this.repositories.add(apodRepository);
// 		this.repositories.add(apodFileRepository);
//         argsParser(args);
//     }

//     public Properties getProps() {
//         return props;
//     }

//     public ObjectMapper getMapper() {
//         return mapper;
//     }    

// 	public NasaService getNasaService() {
// 		return nasaService;
// 	}  

//     public ArrayList<ApodRepository> getRepositories() {
//         return repositories;
//     }

//     private void argsParser(String[] args) {
// 		if (args.length > 0) {
// 			for (int i = 0; i < args.length; i++) {
// 				switch (args[i]) {
// 					case "-s":
// 						props.setProperty("server", "true");
// 						break;
// 					case "-t":
// 						props.setProperty("title", "true");
// 						break;
// 					case "--api":
// 						i++;
// 						props.setProperty("api", args[i]);
// 						break;
// 					default:
// 						log.trace("Unknown argument");
// 						break;
// 				}
// 			}
// 		} else {
// 			try {
// 				props.load(Nasa.class.getClassLoader().getResourceAsStream("application.properties"));
				
// 			} catch (IOException e) {
// 				System.err.println("Configuration file not found");
// 			}
// 		}
//     }
// }
