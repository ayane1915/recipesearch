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
            String name = CreateRecipeForm.getIngredientNames().get(i);
            Integer amount = CreateRecipeForm.getAmounts().get(i);
            String unit = CreateRecipeForm.getUnits().get(i);
            
            // 空の値を除外（最初の1つ以外で、すべて空の場合はスキップ）
            if (i > 0 && (name == null || name.trim().isEmpty()) 
                && (amount == null) 
                && (unit == null || unit.trim().isEmpty())) {
                continue;
            }
            
            Ingredient ingredient = new Ingredient(recipe, name, amount, unit);
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);

        List<Step> steps = new ArrayList<Step>();
        int stepNumber = 1;
        for (int i = 0; i < CreateRecipeForm.getStepDetails().size(); i++) {
            String stepDetail = CreateRecipeForm.getStepDetails().get(i);
            String point = (CreateRecipeForm.getPoints() != null && i < CreateRecipeForm.getPoints().size()) 
                ? CreateRecipeForm.getPoints().get(i) : null;
            
            // 空の値を除外（最初の1つ以外で、手順が空の場合はスキップ）
            if (i > 0 && (stepDetail == null || stepDetail.trim().isEmpty())) {
                continue;
            }
            
            Step step = new Step(recipe, stepNumber, stepDetail, point);
            steps.add(step);
            stepNumber++;
        }
        stepRepository.saveAll(steps);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        ingredientRepository.deleteByRecipe_RecipeId(recipeId);
        stepRepository.deleteByRecipe_RecipeId(recipeId);
    }
}
