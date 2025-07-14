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
@Table(name = "stepstable")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;
    private int stepNumber;
    private String stepDetail;
    private String point;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // 新しいコンストラクタ
    public Step(Recipe recipe, int stepNumber, String stepDetail, String point) {
        this.recipe = recipe;
        this.stepNumber = stepNumber;
        this.stepDetail = stepDetail;
        this.point = point;
    }
}
