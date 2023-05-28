# Mendix Assessment

## Objective
Create the back-end REST services which can be consumed by the web designers.

### Story 1 - As a web designer I would like to retrieve recipes from the back-end system so I can display them in my app

Requirements:
- Without any additional query parameters, should return all recipes known to the back-end service
- Support filtering based on recipe category
- Support search strings, with the service then trying to match these in relevant fields (for example name and category)
### Story 2 - As a web designer I would like to retrieve the available recipe categories so I can do more focused requests for specific recipe types

Requirements:
- Operation returns all recipe categories

### Story 3 - As a web designer I want to be able to add new recipes, so I can expand the recipe database with new, tasty and inspiring recipes

Requirements:
- When given valid input, creates a new recipe in the backend which can then be retrieved by the service's clients
- Make sure the provided input is valid
- Do not allow multiple recipes with the same name (so people don't get confused)
-----------------------------------------
## Mendix

Unfortunately the web designer called in sick today and your product owner really wants to show of the new web site in the board of directors. She asks you to start creating the new web site using Mendix.

### Story 4 - As a home cook I would like to visit a web site listing recipes, so I can get inspiration for what I want to cook tonight

Requirements:
- When navigating to the application, end up on the homepage which shows a recipe overview
- This homepage in split in two sections:
  - On the left all available categories are shown
  - The main panel on the page shows a list of recipes, with enough information (title, time needed) to know what the recipe is about
- If you select a category, the main panel only shows the recipes from that category
- Recipes are retrieved from the previously developed back end system

### Story 5 - As a home cook I want to see the recipe details so I can cook the recipe I picked

Requirements:
- Either by double clicking on the selected recipe or clicking a button, a details page is opened which shows all the details of the selected recipe
-----------------------------------------
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
I've tried to make it as much production ready as I could.

The database H2 is used for fast response, it can be changed to any other relational database. 



-----------------------------------------
## H2 Console
http://localhost:9005/mendix/h2-console/