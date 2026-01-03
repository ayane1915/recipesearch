package com.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // デフォルトコンストラクタ
@Entity
@Table(name = "ingredienstable")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    // @ManyToOneと@JoinColumnを使用してRecipeクラスとの関連付けを行う
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private String ingredientName;
    private Double amount;
    private String unit;

    // 新しいコンストラクタ
    public Ingredient(Recipe recipe, String ingredientName, Double amount, String unit) {
        this.recipe = recipe;
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.unit = unit;
    }
}
