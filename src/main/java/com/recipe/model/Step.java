package com.recipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
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

    // Constructors, getters, and setters
}
