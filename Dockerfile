# Build stage
FROM maven:3.9.1-eclipse-temurin-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM eclipse-temurin:17.0.7_7-jre-focal
ENV PROFILE="dev"
COPY --from=build /home/app/target/Roboshi-1.0-SNAPSHOT.jar /usr/local/lib/roboshi.jar
CMD ["sh", "-c", "java \
    -Dspring.profiles.active=${PROFILE} \
    -Ddiscord.bot.token=${DISCORD_BOT_TOKEN} \
    -Dspring.data.mongodb.username=${DB_USERNAME} \
    -Dspring.data.mongodb.password=${DB_PASSWORD} \
    -Dspotify.client.id=${SPOTIFY_CLIENT_ID} \
    -Dspotify.client.secret=${SPOTIFY_CLIENT_SECRET} \
    -jar /usr/local/lib/roboshi.jar"]
