web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
release: java -cp target/dependency/* com.voronkov.blog.config.flyway.Migrations