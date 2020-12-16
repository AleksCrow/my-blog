web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
migrate: java -cp target/classes:target/dependency/* com.voronkov.blog.config.flyway.Migration