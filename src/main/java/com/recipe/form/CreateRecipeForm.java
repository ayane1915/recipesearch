package com.recipe.form;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateRecipeForm {

	@NotEmpty
	private String recipeName;

	private String recipeSummary;

	private String category;

	@NotEmpty
	private int servings;

	@NotEmpty
	private List<String> ingredientNames;

	@NotEmpty
	private List<String> amounts;

	@NotEmpty
	private List<String> units;

	@NotEmpty
	private List<String> stepDetails;

	private List<String> points;
}