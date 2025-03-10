# Build frontend
FROM node:22-slim AS build-js

WORKDIR /app

# Copy only package.json and package-lock.json first (to optimize caching)
COPY ../frontend/package.json ./

# Install dependencies
RUN npm install

# Copy only necessary files (avoid unnecessary files)
COPY ../frontend/public ./public
COPY ../frontend/src ./src
COPY ../frontend/vite.config.js ./
COPY ../frontend/index.html ./

RUN npm run build

# Build backend
FROM gradle:latest AS build-java

COPY ../backend/ /project
COPY --from=build-js /app/dist /project/globalyouthleadership/src/main/resources/public

RUN cd /project/globalyouthleadership && gradle --no-daemon build

# Run api 
FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY --from=build-java \
    /project/globalyouthleadership/build/libs/globalyouthleadership-0.0.1-SNAPSHOT.jar \
    /app/api.jar

EXPOSE 8080

CMD [ "java", "-jar", "api.jar" ]