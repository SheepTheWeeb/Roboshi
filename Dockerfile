# Build stage
FROM maven:3.9.1-eclipse-temurin-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM eclipse-temurin:17.0.7_7-jre-focal
ENV DISCORD_BOT_TOKEN=""
ENV PROFILE="dev"
COPY --from=build /home/app/target/Roboshi-1.0-SNAPSHOT.jar /usr/local/lib/roboshi.jar
ENTRYPOINT ["sh", "-c", "java -DdiscordBotToken=$DISCORD_BOT_TOKEN -jar /usr/local/lib/roboshi.jar $PROFILE"]
