FROM maven:3.9.1-amazoncorretto-20 as module-build

WORKDIR /module/build

COPY . .

RUN mvn clean package

FROM openjdk:20-jdk-slim as production

RUN useradd -ms /bin/bash mendix-recipe

USER mendix-recipe

WORKDIR /app

COPY --from=module-build --chown=mendix-recipe:mendix-recipe /module/build/target/mendix-recipe-0.0.1-SNAPSHOT.jar ./mendix-recipe-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","mendix-recipe-0.0.1-SNAPSHOT.jar"]