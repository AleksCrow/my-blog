release: java $JAVA_OPTS -cp target/classes:target/dependency/* com.voronkov.blog.config.flyway.Migrations
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war