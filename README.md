# Mendix Assessment

## Setup instruction

#### Prerequisite

- Git 2.x
- Container 
  - Docker 
  - Docker-Compose
- Alternative
  - Maven 3.x
  - Java 11

Clone repository

```bash
git clone https://github.com/razi-ahmad/mendix-recipe.git
```

### Run application with docker

  ```bash
  docker-compose -p mendix-recipe up -d
  ```
-----------------------------------------
## Run application without docker 
Build project

  ```bash
  mvn clean package
  ```
Run Project
  ```bash
  java -jar target/mendix-recipe-0.0.1-SNAPSHOT.jar
  ```
-----------------------------------------

Open the swagger interface in the browser

* http://localhost:9005/mendix/swagger-ui/index.html

-----------------------------------------

Run test cases

  ```bash
  mvn clean test
  ```

-----------------------------------------
## Solution
The database H2 is used for fast response, it can be changed to any other relational database. 



-----------------------------------------
## H2 Console
http://localhost:9005/mendix/h2-console/