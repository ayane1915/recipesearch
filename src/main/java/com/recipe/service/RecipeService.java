package com.recipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.form.CreateRecipeForm;
import com.recipe.form.SearchRecipeForm;
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

    public List<Recipe> searchRecipes(SearchRecipeForm SearchQueryParams) {
        if (SearchQueryParams.getRecipeName() != null && !SearchQueryParams.getRecipeName().isEmpty()) {
            return recipeRepository.findByRecipeNameContaining(SearchQueryParams.getRecipeName());
        } else if (SearchQueryParams.getIngredientName() != null && !SearchQueryParams.getIngredientName().isEmpty()) {
            return ingredientRepository.findByIngredientNameContaining(SearchQueryParams.getIngredientName()).stream()
                    .map(ingredient -> ingredient.getRecipe())
                    .distinct()
                    .toList();
        }
        return List.of();
    }

    public void addRecipe(CreateRecipeForm CreateRecipeForm) {
        Recipe recipe = new Recipe(
            CreateRecipeForm.getRecipeName(), 
            CreateRecipeForm.getRecipeSummary(), 
            CreateRecipeForm.getCategory(),
            CreateRecipeForm.getServings()
        );
        recipeRepository.save(recipe);

        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        for (int i = 0; i < CreateRecipeForm.getIngredientNames().size(); i++) {
            Ingredient ingredient = new Ingredient(recipe, CreateRecipeForm.getIngredientNames().get(i), CreateRecipeForm.getAmounts().get(i), CreateRecipeForm.getUnits().get(i));
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);

        List<Step> steps = new ArrayList<Step>();
        for (int i = 0; i < CreateRecipeForm.getStepDetails().size(); i++) {
            Step step = new Step(recipe, i + 1, CreateRecipeForm.getStepDetails().get(i), CreateRecipeForm.getPoints().get(i));
            steps.add(step);
            }
        stepRepository.saveAll(steps);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        ingredientRepository.deleteByRecipe_RecipeId(recipeId);
        stepRepository.deleteByRecipe_RecipeId(recipeId);
    }
}
