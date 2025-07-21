package com.recipe.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // デフォルトコンストラクタ
@Entity
@Table(name = "recipestable")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    private String recipeName;
    private String recipeSummary;
    private String category;
    
    @Column(nullable = false)
    @Min(value = 1, message = "人数は1人以上を指定してください")
    private int servings;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    // 必要なフィールドのみを引数に持つコンストラクタ
    public Recipe(String recipeName, String recipeSummary, String category, int servings) {
        this.recipeName = recipeName;
        this.recipeSummary = recipeSummary;
        this.category = category;
        this.servings = servings;
    }
}
