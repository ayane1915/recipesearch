package com.recipe.form;

import java.util.List;
import lombok.Data;

@Data
public class RecalculateForm {
    private Long recipeId;
    private int originalServings;
    private int newServings;
    private List<String> ingredientNames;
    private List<String> amounts;
    private List<String> units;
}