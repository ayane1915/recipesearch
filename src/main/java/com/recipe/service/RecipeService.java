package com.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.model.Ingredient;
import com.recipe.model.Recipe;
import com.recipe.model.Step;
import com.recipe.repository.IngredientRepository;
import com.recipe.repository.RecipeRepository;
import com.recipe.repository.StepRepository;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private StepRepository stepRepository;

    public List<Recipe> searchRecipes(String recipeName, String ingredientName) {
        if (recipeName != null && !recipeName.isEmpty()) {
            return recipeRepository.findByRecipeNameContaining(recipeName);
        } else if (ingredientName != null && !ingredientName.isEmpty()) {
            return ingredientRepository.findByIngredientNameContaining(ingredientName).stream()
                    .map(ingredient -> ingredient.getRecipe())
                    .distinct()
                    .toList();
        }
        return List.of();
    }

    public void addRecipe(String recipeName, String recipeSummary, String category, List<String> ingredientNames,
                          List<String> amounts, List<String> units, List<String> stepDetails, List<String> points) {
        Recipe recipe = new Recipe(recipeName, recipeSummary, category);
        recipeRepository.save(recipe);
        for (int i = 0; i < ingredientNames.size(); i++) {
            Ingredient ingredient = new Ingredient(recipe, ingredientNames.get(i), amounts.get(i), units.get(i));
            ingredientRepository.save(ingredient);
        }
        for (int i = 0; i < stepDetails.size(); i++) {
            Step step = new Step(recipe, i + 1, stepDetails.get(i), points.get(i));
            stepRepository.save(step);
        }
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        ingredientRepository.deleteByRecipe_RecipeId(recipeId);
        stepRepository.deleteByRecipe_RecipeId(recipeId);
    }
}
