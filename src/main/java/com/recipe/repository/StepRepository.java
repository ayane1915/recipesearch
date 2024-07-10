package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.model.Step;

public interface StepRepository extends JpaRepository<Step, Long> {
    void deleteByRecipeId(Long recipeId);
}
