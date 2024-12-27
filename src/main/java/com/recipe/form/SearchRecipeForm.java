package com.recipe.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRecipeForm {

	@NotEmpty
	private String recipeName;

	@NotEmpty
	private String ingredientName;
}