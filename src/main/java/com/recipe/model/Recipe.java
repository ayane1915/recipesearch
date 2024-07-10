package com.recipe.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    private String recipeName;
    private String recipeSummary;
    private String category;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    // デフォルトコンストラクタ
    public Recipe() {}

    // 新しいコンストラクタ
    public Recipe(String recipeName, String recipeSummary, String category) {
        this.recipeName = recipeName;
        this.recipeSummary = recipeSummary;
        this.category = category;
    }

    //getterとsetter
	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeSummary() {
		return recipeSummary;
	}

	public void setRecipeSummary(String recipeSummary) {
		this.recipeSummary = recipeSummary;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
