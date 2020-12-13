release: java -cp target/classes:target/dependency/* config.flyway.Migrations
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war