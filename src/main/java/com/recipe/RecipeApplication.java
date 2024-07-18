package com.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.recipe.controller.RecipeController;
import com.recipe.model.Ingredient;
import com.recipe.model.Recipe;
import com.recipe.model.Step;
import com.recipe.repository.IngredientRepository;
import com.recipe.repository.RecipeRepository;
import com.recipe.repository.StepRepository;
import com.recipe.service.RecipeService;

@SpringBootApplication
@ComponentScan(basePackageClasses = { RecipeController.class, RecipeService.class, IngredientRepository.class, RecipeRepository.class, StepRepository.class, Ingredient.class, Recipe.class, Step.class })
public class RecipeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipeApplication.class, args);
    }
}
