package com.recipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;
    private Long recipeId;
    private String ingredientName;
    private String amount;
    private String unit;

    // デフォルトコンストラクタ
    public Ingredient() {}

    // 新しいコンストラクタ
    public Ingredient(Long recipeId, String ingredientName, String amount, String unit) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.unit = unit;
    }

    //getterとsetter
    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
