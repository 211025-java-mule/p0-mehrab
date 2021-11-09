package com.github.MehrabRahman.nasa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class ApodPostgresRepository implements ApodRepository{
    private Connection connection;

    public ApodPostgresRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(Apod output) {
		try {
			PreparedStatement statement = connection.prepareStatement("insert into apod(copyright, day, explanation, media_type, service_version, title, url, hdurl) values (?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, output.getCopyright());
			statement.setDate(2, Date.valueOf(output.getDate()));
			statement.setString(3, output.getExplanation());
			statement.setString(4, output.getMedia_type());
			statement.setString(5, output.getService_version());
			statement.setString(6, output.getTitle());
			statement.setString(7, output.getUrl());
			statement.setString(8, output.getHdurl());
			statement.executeUpdate();
			statement.close();
			// statement = connection.prepareStatement("select * from apod");
			// ResultSet rs = statement.executeQuery();
			// while (rs.next()) {
			// 	System.out.println("SQL: " + rs.getString("title"));
			// }
			// rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}
