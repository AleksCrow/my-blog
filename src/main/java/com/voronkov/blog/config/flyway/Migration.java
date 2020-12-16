package com.voronkov.blog.config.flyway;

import org.flywaydb.core.Flyway;

public class Migration {

	public static void main(String[] args) throws Exception {
        Flyway flyway = Flyway.configure()
        		.dataSource(System.getenv("JDBC_DATABASE_URL"),
        				System.getenv("JDBC_DATABASE_USERNAME"),
        				System.getenv("JDBC_DATABASE_PASSWORD"))
        		.load();
        flyway.migrate();
    }
}
