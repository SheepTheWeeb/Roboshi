# Build stage
FROM maven:3.9.1-eclipse-temurin-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM eclipse-temurin:17.0.7_7-jre-focal
ENV PROFILE="dev"
ENV DISCORD_BOT_TOKEN=""
ENV DB_CONNECTION_STRING=""
COPY --from=build /home/app/target/Roboshi-1.0-SNAPSHOT.jar /usr/local/lib/roboshi.jar
ENTRYPOINT ["sh", "-c", "java -jar /usr/local/lib/roboshi.jar --spring.profiles.active=$PROFILE --discordBotToken=$DISCORD_BOT_TOKEN --spring.data.mongodb.uri=$DB_CONNECTION_STRING"]
