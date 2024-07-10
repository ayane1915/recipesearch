package com.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	// 依存性の注入
	@Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/search")
    public String searchRecipes(@RequestParam(required = false) String recipeName, @RequestParam(required = false) String ingredientName, Model model) {

       List<Recipe> recipes = recipeService.searchRecipes(recipeName, ingredientName);
        if (recipes.isEmpty()) {
            model.addAttribute("message", "検索結果0件");
        } else {
            model.addAttribute("recipes", recipes);
        }
        return "search";
    }

    @GetMapping("/add")
    public String showAddRecipePage() {
        return "addRecipe";
    }

    @PostMapping("/add")
    public String addRecipe(@RequestParam String recipeName, @RequestParam String recipeSummary, @RequestParam String category,
                            @RequestParam List<String> ingredientNames, @RequestParam List<String> amounts, @RequestParam List<String> units,
                            @RequestParam List<String> stepDetails, @RequestParam List<String> points, Model model) {
        if (recipeName.isEmpty() || ingredientNames.isEmpty() || amounts.isEmpty() || units.isEmpty() || stepDetails.isEmpty()) {
            model.addAttribute("errorMessage", "必須項目を入力してください。");
            return "addRecipe";
        }
        recipeService.addRecipe(recipeName, recipeSummary, category, ingredientNames, amounts, units, stepDetails, points);
        model.addAttribute("message", "登録が完了しました。");
        return "addRecipe";
    }

    @PostMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId, Model model) {
        recipeService.deleteRecipe(recipeId);
        model.addAttribute("message", "レシピを削除しました。");
        return "redirect:/";
    }
}
