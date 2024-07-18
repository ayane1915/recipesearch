package com.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	List<Ingredient> findByIngredientNameContaining(String ingredientName);

	void deleteByRecipe_RecipeId(Long recipeId);
//	void deleteByRecipeId(Long recipeId);
}
