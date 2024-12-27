package com.recipe.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.recipe.form.CreateRecipeForm;
import com.recipe.form.SearchRecipeForm;
import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;

import jakarta.validation.Valid;

@Controller
//@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/search")
    public String searchRecipes(@ModelAttribute @Valid SearchRecipeForm SearchQueryParams, BindingResult bindingResult, Model model) {
        List<Recipe> recipes = recipeService.searchRecipes(SearchQueryParams);
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
    public String addRecipe(@ModelAttribute @Valid CreateRecipeForm CreateRecipeForm, BindingResult bindingResult, Model model) {

    	if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "必須項目を入力してください。");
            return "addRecipe";
        }
//    	for (String amount : amounts) {
//    	    if (!amount.matches("[\\x00-\\x7F]+")) {
//    	    	checkFullWidth = true;
//    	        break;
//    	    }
//    	}
//        if (checkFullWidth) {
//            model.addAttribute("errorMessage", "半角数字を入力してください。");
//            return "addRecipe";
//        }

        recipeService.addRecipe(CreateRecipeForm);
        model.addAttribute("message", "登録が完了しました。");
        return "addRecipe";
    }

    @PostMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return "redirect:/search";
    }
}
