DROP DATABASE IF EXISTS vegan_kitchen_api;
CREATE DATABASE vegan_kitchen_api;
USE vegan_kitchen_api;
CREATE TABLE recipe (
  recipe_id INT UNIQUE NOT NULL AUTO_INCREMENT,
  dish_name VARCHAR(255) NOT NULL,
  summary VARCHAR(255) NOT NULL,
  serving VARCHAR(50) NOT NULL,
  dish_image_url VARCHAR(255) NOT NULL,
  author_user_id INT NOT NULL,
  ingredient_id INT NOT NULL,
  PRIMARY KEY (recipe_id)
);
CREATE TABLE recipe_instruction (
  recipe_id INT NOT NULL,
  instruction_id INT UNIQUE NOT NULL AUTO_INCREMENT,
  step_number int NOT NULL,
  instruction VARCHAR(255) NOT NULL,
  image_url VARCHAR(255),
  PRIMARY KEY (instruction_id)
);
CREATE TABLE user (
  user_id INT UNIQUE NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  is_blocked BOOL,
  PRIMARY KEY (user_id)
);
CREATE TABLE logging (
  user_id INT UNIQUE NOT NULL,
  session_id VARCHAR(255) UNIQUE NOT NULL,
  exception VARCHAR(225),
  message VARCHAR(500)
);

CREATE TABLE ingredient_recipe (
  name VARCHAR(50) NOT NULL,
  recipe_id INT NOT NULL,
  image_url VARCHAR(225),
  ingredient_id INT NOT NULL
);

CREATE TABLE user_attempt (
  user_id INT UNIQUE NOT NULL,
  attempt_number INT,
  message VARCHAR(225),
  session_id VARCHAR(225) UNIQUE NOT NULL
)