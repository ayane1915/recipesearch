package com.recipe.service;

import java.util.ArrayList;
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


    	//レシピ名が入力された場合、レシピ名で検索
    	if (recipeName != null && !recipeName.isEmpty()) {
            return recipeRepository.findByRecipeNameContaining(recipeName);

        //材料名が入力された場合、材料名で検索
        } else if (ingredientName != null && !ingredientName.isEmpty()) {
            List<Ingredient> ingredients = ingredientRepository.findByIngredientNameContaining(ingredientName);
            List<Recipe> recipes = new ArrayList<>();

            //材料名からレシピIDを検索しレシピ名を取得
            for (Ingredient ingredient : ingredients) {
                Recipe recipe = recipeRepository.findById(ingredient.getRecipeId()).orElse(null);

                //検索したレシピがあった場合 && レシピが重複していない場合
                if (recipe != null && !recipes.contains(recipe)) {
                    recipes.add(recipe);
                }
            }
            return recipes;
        }
        return new ArrayList<>();
    }

    public void addRecipe(String recipeName, String recipeSummary, String category, List<String> ingredientNames,
            List<String> amounts, List<String> units, List<String> stepDetails, List<String> points) {

    	Recipe recipe = new Recipe(recipeName, recipeSummary, category);
		recipeRepository.save(recipe);

		for (int i = 0; i < ingredientNames.size(); i++) {
			Ingredient ingredient = new Ingredient(recipe.getRecipeId(), ingredientNames.get(i), amounts.get(i), units.get(i));
			ingredientRepository.save(ingredient);
		}

		for (int i = 0; i < stepDetails.size(); i++) {
			Step step = new Step(recipe, i + 1, stepDetails.get(i), points.get(i));
			stepRepository.save(step);
		}
	}

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        ingredientRepository.deleteByRecipeId(recipeId);
        stepRepository.deleteByRecipeId(recipeId);
    }
}
