<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>レシピ検索</title>
</head>
<body>
<h1>Recipi Research</h1>
<form action="/search" method="get">
    <label for="recipeName">レシピ名:</label>
    <input type="text" id="recipeName" name="recipeName">
    <label for="ingredientName">食材名:</label>
    <input type="text" id="ingredientName" name="ingredientName">
    <button type="submit">検索</button>
</form>
<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>
<c:if test="${not empty recipes}">
    <ul>
        <c:forEach var="recipe" items="${recipes}">
            <li>${recipe.recipeName}</li>
            <form action="/delete/${recipe.recipeId}" method="post">
                <button type="submit">削除</button>
            </form>
        </c:forEach>
    </ul>
</c:if>
<a href="/add">レシピ追加</a>
</body>
</html>
