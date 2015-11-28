USE vegan_kitchen_api;
INSERT INTO
  `recipe` (`recipe_id`, `dish_name`, `summary`, `serving`, `dish_image_url`, `author_user_id`)
VALUES
  (1, "dish name 1", "summary 1", "serving 1", "dish.image.1.com", 1),
  (2, "dish name 2", "summary 2", "serving 2", "dish.image.2.com", 1);
INSERT INTO
  `recipe_instruction` (`recipe_id`, `instruction_id`, `step_number`, `instruction`, `image_url`)
VALUES
  (1, 1, 1, "instruction 1", "instruction1.com"),
  (1, 2, 2, "instruction 2", "instruction2.com"),
  (2, 3, 1, "instruction 1", "instruction3.com"),
  (2, 4, 2, "instruction 2", "instruction4.com"),
  (2, 5, 3, "instruction 3", "instruction5.com");
INSERT INTO
  `recipe_ingredient` (`recipe_id`, `ingredient_id`, `name`, `amount`, `image_url`)
VALUES
  (1, 1, "ingredient 1", "10 tons", "ingredient1.com"),
  (1, 2, "ingredient 2", "20 tons", "ingredient2.com"),
  (1, 3, "ingredient 3", "30 tons", "ingredient3.com"),
  (2, 4, "ingredient 4", "40 tons", "ingredient4.com"),
  (2, 5, "ingredient 5", "50 tons", "ingredient5.com");