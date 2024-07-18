package com.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    // デフォルトコンストラクタ
    public Step() {}

    // 新しいコンストラクタ
    public Step(Recipe recipe, int stepNumber, String stepDetail, String point) {
        this.recipe = recipe;
        this.stepNumber = stepNumber;
        this.stepDetail = stepDetail;
        this.point = point;
    }
    //getterとsetter
	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getStepDetail() {
		return stepDetail;
	}

	public void setStepDetail(String stepDetail) {
		this.stepDetail = stepDetail;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

}
