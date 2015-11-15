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
  PRIMARY KEY (recipe_id)
);
CREATE TABLE cooking_instruction (
  recipe_id INT NOT NULL,
  instruction_id INT UNIQUE NOT NULL AUTO_INCREMENT,
  instruction VARCHAR(255) NOT NULL,
  image_url VARCHAR(255),
  PRIMARY KEY (instruction_id)
);
CREATE TABLE api_user (
  user_id INT UNIQUE NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
);
CREATE TABLE application_logging (
  user_id INT UNIQUE NOT NULL,
  session_id VARCHAR(255) UNIQUE NOT NULL
)