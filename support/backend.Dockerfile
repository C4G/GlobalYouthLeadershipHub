# Build backend
FROM gradle:latest AS build

COPY ../backend/ /project

RUN cd /project/globalyouthleadership && gradle --no-daemon build

# Run api 
FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY --from=build \
    /project/globalyouthleadership/build/libs/globalyouthleadership-0.0.1-SNAPSHOT.jar \
    /app/api.jar

EXPOSE 8080

CMD [ "java", "-jar", "api.jar" ]