FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod 777 mvnw && ./mvnw dependency:resolve
COPY src ./src

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8083
COPY --from=build /app/target/microgc-*.jar /microgc.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/microgc.jar"]