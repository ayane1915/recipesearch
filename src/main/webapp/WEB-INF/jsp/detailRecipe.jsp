<%@ page contentType="text/html; charset=UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="ja">

        <head>
            <meta charset="UTF-8">
            <title>レシピ詳細</title>
            <link rel="stylesheet" href="/css/common.css">
            <link rel="stylesheet" href="/css/detailRecipe.css">
        </head>

        <body class="detail-container">
            <h1>Detail Recipe</h1>
            <div class="recipe_detail">
                <c:if test="${not empty recipe}">
                    <ul>
                        <a>
                            <li>${recipe.recipeName}</li>
                        </a>
                        <ul>
                            <li>説明 : ${recipe.recipeSummary}</li>
                            <li>カテゴリ : ${recipe.category}</li>
                            <li>人数 :
                                <form action="/recalculate/${recipe.recipeId}" method="get" style="display: inline;">
                                    <input type="hidden" name="originalServings" value="${recipe.servings}">
                                    <c:if test="${not empty param.recipeName}">
                                        <input type="hidden" name="recipeName" value="${param.recipeName}">
                                    </c:if>
                                    <c:if test="${not empty param.ingredientName}">
                                        <input type="hidden" name="ingredientName" value="${param.ingredientName}">
                                    </c:if>
                                    <select name="newServings">
                                        <c:forEach var="i" begin="1" end="6">
                                            <option value="${i}" ${recipe.servings eq i ? 'selected' : '' }>
                                                ${i}人分
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" value="再計算">
                                </form>
                            </li>
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
                            <button type="submit" class="delete-button">delete</button>
                        </form>
                        <div class="detail-bottom-submit">
                            <a href="/search?recipeName=${searchQueryParams.recipeName}&ingredientName=${searchQueryParams.ingredientName}"
                                style="text-decoration: none; margin-left: 10px;"><button type="button"
                                    class="detail-button">back</button></a>
                        </div>
                    </ul>
                </c:if>
            </div>
        </body>

        </html>