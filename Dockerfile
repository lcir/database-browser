FROM gradle:jdk16-openj9 as builder

WORKDIR /app
COPY ./ /app

## Build a release artifact.
RUN gradle clean
RUN gradle build -x test

RUN java -Djarmode=layertools -jar /app/app/build/libs/*.jar extract
FROM openjdk:16-slim
VOLUME /tmp

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
