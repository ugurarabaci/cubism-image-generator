FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /app/target/CubismImageGenerator-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
ENV SPRING_APPLICATION_NAME=CubismImageGenerator
ENV HUGGINGFACE_API_TOKEN=${HUGGINGFACE_API_TOKEN}
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=10MB
ENV SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=10MB
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"] 