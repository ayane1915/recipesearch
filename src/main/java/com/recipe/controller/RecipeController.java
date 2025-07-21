package com.recipe.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.StringUtils;

import com.recipe.form.CreateRecipeForm;
import com.recipe.form.SearchRecipeForm;
import com.recipe.model.Recipe;
import com.recipe.service.RecipeService;
import com.recipe.form.RecalculateForm;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

@Controller
// @RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String showSearchPage() {
        return "search";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/search")
    public String searchRecipes(@ModelAttribute @Valid SearchRecipeForm searchQueryParams, BindingResult bindingResult,
            Model model, HttpSession session) {

        // 戻るボタンから遷移
        if (!StringUtils.hasText(searchQueryParams.getRecipeName()) &&
                !StringUtils.hasText(searchQueryParams.getIngredientName())) {

            List<Recipe> searchResults = (List<Recipe>) session.getAttribute("searchResults");
            List<Long> deletedIds = (List<Long>) session.getAttribute("deletedRecipeIds");

            // 削除済みIDを除外
            if (Objects.nonNull(deletedIds)) {
                searchResults = searchResults.stream()
                        .filter(recipe -> !deletedIds.contains(recipe.getRecipeId()))
                        .collect(Collectors.toList());
            }

            if (Objects.nonNull(searchResults)) {
                model.addAttribute("recipes", searchResults);
            } else {
                model.addAttribute("message", "検索結果0件");
            }
            return "search";
        }

        // 検索結果表示
        List<Recipe> recipes = recipeService.searchRecipes(searchQueryParams);
        session.setAttribute("searchQueryParams", searchQueryParams);
        session.setAttribute("searchResults", recipes);

        if (recipes.isEmpty()) {
            model.addAttribute("message", "検索結果0件");
        } else {
            model.addAttribute("recipes", recipes);
        }
        return "search";
    }

    @GetMapping("/detail/{recipeId}")
    public String showDetailRecipePage(@PathVariable Long recipeId, @RequestParam(required = false) String recipeName,
            @RequestParam(required = false) String ingredientName, Model model, HttpSession session) {
        // セッションから再計算結果を取得
        Recipe recalculatedRecipe = (Recipe) session.getAttribute("recalculatedRecipe");
        String message = (String) session.getAttribute("message");

        if (recalculatedRecipe != null && recalculatedRecipe.getRecipeId().equals(recipeId)) {
            // 再計算結果がある場合はそれを使用
            model.addAttribute("recipe", recalculatedRecipe);
            if (message != null) {
                model.addAttribute("message", message);
            }

            // セッションから削除（一度使用したら消す）
            session.removeAttribute("recalculatedRecipe");
            session.removeAttribute("message");
        } else {
            // 通常の処理
            Recipe recipe = recipeService.findById(recipeId);
            model.addAttribute("recipe", recipe);
        }

        return "detailRecipe";
    }

    @GetMapping("/add")
    public String showAddRecipePage() {
        return "addRecipe";
    }

    @PostMapping("/add")
    public String addRecipe(@ModelAttribute @Valid CreateRecipeForm CreateRecipeForm, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "必須項目を入力してください。");
            return "addRecipe";
        }

        recipeService.addRecipe(CreateRecipeForm);
        model.addAttribute("message", "登録が完了しました。");
        return "addRecipe";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId, HttpSession session) {
        recipeService.deleteRecipe(recipeId);

        // 削除済みIDをセッションに保存
        List<Long> deletedIds = (List<Long>) session.getAttribute("deletedRecipeIds");
        if (deletedIds == null) {
            deletedIds = new ArrayList<>();
        }
        deletedIds.add(recipeId);
        session.setAttribute("deletedRecipeIds", deletedIds);

        return "redirect:/search";
    }

    // 人数調整
    @GetMapping("/recalculate/{recipeId}")
    public String recalculateServings(@PathVariable Long recipeId,
            @ModelAttribute RecalculateForm form,
            @RequestParam(required = false) String recipeName,
            @RequestParam(required = false) String ingredientName,
            HttpSession session) {
        try {
            // サービス層で再計算実行
            Recipe recalculatedRecipe = recipeService.recalculateServings(recipeId, form);

            // セッションに再計算結果を保存
            session.setAttribute("recalculatedRecipe", recalculatedRecipe);
            session.setAttribute("message", "人数を調整しました");

        } catch (RuntimeException e) {
            session.setAttribute("errorMessage", "レシピが見つかりません");
        }

        // 検索条件をURLパラメータとして追加 のちに修正
        StringBuilder redirectUrl = new StringBuilder();
        if (StringUtils.hasText(recipeName) || StringUtils.hasText(ingredientName)) {
            redirectUrl.append("?recipeName=").append(recipeName != null ? recipeName : "");
            redirectUrl.append("&ingredientName=").append(ingredientName != null ? ingredientName : "");
        }

        return "redirect:/detail/" + recipeId;
    }

    @GetMapping("/search-after-delete")
    public String searchAfterDelete(Model model, HttpSession session) {
        // 削除後の検索結果を再取得
        SearchRecipeForm savedParams = (SearchRecipeForm) session.getAttribute("searchQueryParams");
        if (savedParams != null) {
            List<Recipe> recipes = recipeService.searchRecipes(savedParams);
            model.addAttribute("recipes", recipes);
            model.addAttribute("searchQueryParams", savedParams);
        }

        return "search";
    }
}
