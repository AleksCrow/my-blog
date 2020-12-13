package com.voronkov.blog.config.flyway;

import java.net.URI;

import org.flywaydb.core.Flyway;

public class Migrations {
	
	public static void main(String[] args) throws Exception {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		
        Flyway flyway = Flyway
        		.configure()
        		.dataSource(dbUrl, username, password)
    			.load();
        flyway.migrate();
    }
}
