<%@ page contentType="text/html; charset=UTF-8" %>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html lang="ja">

		<head>
			<meta charset="UTF-8">
			<title>レシピ検索</title>
			<link rel="stylesheet" href="/css/common.css">
			<link rel="stylesheet" href="/css/search.css">
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
								<a
									href="/detail/${recipe.recipeId}?recipeName=${searchQueryParams.recipeName}&ingredientName=${searchQueryParams.ingredientName}">
									<li>${recipe.recipeName}</li>
								</a>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</div>
		</body>

		</html>