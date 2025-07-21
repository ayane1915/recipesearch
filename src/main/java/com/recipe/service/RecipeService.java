package com.recipe.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.recipe.form.CreateRecipeForm;
import com.recipe.form.SearchRecipeForm;
import com.recipe.form.RecalculateForm;
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
        if (StringUtils.hasText(SearchQueryParams.getRecipeName())) {
            return recipeRepository.findByRecipeNameContaining(SearchQueryParams.getRecipeName());
            
        } else if (StringUtils.hasText(SearchQueryParams.getIngredientName())) {
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
            // フロントエンドで半角数字に変換済みなので、直接変換
            Double amount = Double.parseDouble(CreateRecipeForm.getAmounts().get(i));
            
            Ingredient ingredient = new Ingredient(
                recipe, 
                CreateRecipeForm.getIngredientNames().get(i), 
                amount,
                CreateRecipeForm.getUnits().get(i)
            );
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

    public Recipe findById(Long recipeId) {
        return recipeRepository.findById(recipeId)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));
    }

    public Recipe recalculateServings(Long recipeId, RecalculateForm form) {
        // 既存のレシピを取得
        Recipe existingRecipe = findById(recipeId);
        
        // 再計算実行
        return recalculateServingsInternal(form, existingRecipe);
    }

    private Recipe recalculateServingsInternal(RecalculateForm form, Recipe existingRecipe) {
        Recipe recalculatedRecipe = new Recipe();
        // 基本情報のコピー
        recalculatedRecipe.setRecipeId(existingRecipe.getRecipeId());
        recalculatedRecipe.setRecipeName(existingRecipe.getRecipeName());
        recalculatedRecipe.setRecipeSummary(existingRecipe.getRecipeSummary());
        recalculatedRecipe.setCategory(existingRecipe.getCategory());
        recalculatedRecipe.setServings(form.getNewServings());
        
        // 材料の再計算
        List<Ingredient> recalculatedIngredients = new ArrayList<>();
        for (Ingredient originalIngredient : existingRecipe.getIngredients()) {
            // 1人分計算
            double baseAmount = originalIngredient.getAmount() / existingRecipe.getServings();
            // 人数分再計算
            double newAmount = baseAmount * form.getNewServings();
            
            // コンストラクタを使用してIngredientを作成
            Ingredient newIngredient = new Ingredient(
                recalculatedRecipe, // recipeフィールドを設定
                originalIngredient.getIngredientName(),
                newAmount,
                originalIngredient.getUnit()
            );
            
            recalculatedIngredients.add(newIngredient);
        }
        
        // 再計算した材料リスト
        recalculatedRecipe.setIngredients(recalculatedIngredients);
        
        // 手順の再設定（recipeフィールドを更新）
        List<Step> recalculatedSteps = new ArrayList<>();
        for (Step originalStep : existingRecipe.getSteps()) {
            Step newStep = new Step(
                recalculatedRecipe, // recipeフィールドを設定
                originalStep.getStepNumber(),
                originalStep.getStepDetail(),
                originalStep.getPoint()
            );
            recalculatedSteps.add(newStep);
        }
        recalculatedRecipe.setSteps(recalculatedSteps);
        
        return recalculatedRecipe;
    }
}
