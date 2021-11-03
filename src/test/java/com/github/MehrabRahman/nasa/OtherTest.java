package com.github.MehrabRahman.nasa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class OtherTest {
    @Test
    public void bleh() {
        assertTrue("message", true);
    }

    @Test
    public void dbTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;INIT=runscript from 'classpath:schema.sql';MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE", "sa", "");
        Statement statement = connection.createStatement();
        // statement.execute("create table if not exists apod(title varchar(150))");
        statement.execute("insert into apod(title) values ('test')");
        ResultSet rs = statement.executeQuery("select * from apod where title = 'test'");
        while (rs.next()) {
            assertEquals("test", rs.getString("title"));;
        }
        rs.close();
        statement.close();
        connection.close();
    }
}
