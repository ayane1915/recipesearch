<%@ page contentType="text/html; charset=UTF-8" %>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html lang="ja">

		<head>
			<meta charset="UTF-8">
			<title>レシピ検索</title>
			<link rel="stylesheet" type="text/css" href="/css/search.css">
		</head>

		<body>
			<h1>Recipi Research</h1>
			<div class=search_body>
				<form action="/search" method="get" class=search_form>
					<label for="recipeName">recipeName:</label> <input type="text" id="recipeName" name="recipeName"
						placeholder="レシピ"> <label for="ingredientName">ingredientName:</label> <input type="text"
						id="ingredientName" name="ingredientName" placeholder="食材">
					<button type="submit" class=search_submit>search</button>
				</form>

				<a href="/add">AddRecipe</a>
				<c:if test="${not empty message}">
					<p>${message}</p>
				</c:if>

				<div class=recipe_result>
					<c:if test="${not empty recipes}">
						<ul>
							<c:forEach var="recipe" items="${recipes}">
								<li>${recipe.recipeName}</li>
								<ul>
									<li>説明 : ${recipe.recipeSummary}</li>
									<li>カテゴリ : ${recipe.category}</li>
									<li>人数 : ${recipe.servings}人前</li>
									<li>材料</li>
									<ul>
										<c:forEach var="ingredient" items="${recipe.ingredients}">
											<li>${ingredient.ingredientName}-${ingredient.amount}
												${ingredient.unit}</li>
										</c:forEach>
									</ul>
									<li>手順</li>
									<ul>
										<c:forEach var="step" items="${recipe.steps}" varStatus="loop">
											<li>ステップ ${loop.index + 1}: ${step.stepDetail} - ポイント
												${step.point}</li>
										</c:forEach>
									</ul>
								</ul>
								<form action="/delete/${recipe.recipeId}" method="post">
									<button type="submit" class="remove-btn">delete</button>
								</form>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
		</body>

		</html>