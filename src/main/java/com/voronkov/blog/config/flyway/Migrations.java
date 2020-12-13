package com.voronkov.blog.config.flyway;

import org.flywaydb.core.Flyway;

public class Migrations {
	
	public static void main(String[] args) throws Exception {
        Flyway flyway = Flyway
        		.configure()
        		.dataSource(System.getenv("DATABASE_URL"),
        				System.getenv("DATABASE_USERNAME"),
        				System.getenv("DATABASE_PASSWORD"))
    			.load();
        flyway.migrate();
    }
}
