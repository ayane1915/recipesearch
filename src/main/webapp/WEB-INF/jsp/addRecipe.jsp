<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>Add Recipe</title>
    <script>
        function addIngredient() {
            var ingredientDiv = document.createElement('div');
            ingredientDiv.innerHTML = `
                <label for="ingredientName">材料:</label>
                <input type="text" name="ingredientNames" required>
                <label for="amount">量:</label>
                <input type="text" name="amounts" required>
                <label for="unit">単位:</label>
                <input type="text" name="units" required>
            `;
            document.getElementById('ingredients').appendChild(ingredientDiv);
        }

        function addStep() {
            var stepDiv = document.createElement('div');
            stepDiv.innerHTML = `
                <label for="stepDetail">工程:</label>
                <textarea name="stepDetails" required></textarea>
                <label for="point">ポイント:</label>
                <textarea name="points"></textarea>
            `;
            document.getElementById('steps').appendChild(stepDiv);
        }
    </script>
</head>
<body>
    <h1>レシピ追加</h1>
    <form action="/recipe/add" method="post">
        <label for="recipeName">レシピ名:</label>
        <input type="text" id="recipeName" name="recipeName" required>
        <label for="recipeSummary">レシピ概要:</label>
        <textarea id="recipeSummary" name="recipeSummary"></textarea>
        <label for="category">カテゴリ:</label>
        <input type="text" id="category" name="category">

        <h2>材料</h2>
        <div id="ingredients">
            <div>
                <label for="ingredientName">材料:</label>
                <input type="text" name="ingredientNames" required>
                <label for="amount">量:</label>
                <input type="text" name="amounts" required>
                <label for="unit">単位:</label>
                <input type="text" name="units" required>
            </div>
        </div>
        <button type="button" onclick="addIngredient()">材料追加</button>

        <h2>工程</h2>
        <div id="steps">
            <div>
                <label for="stepDetail">工程内容:</label>
                <textarea name="stepDetails" required></textarea>
                <label for="point">ポイント:</label>
                <textarea name="points"></textarea>
            </div>
        </div>
        <button type="button" onclick="addStep()">工程追加</button>

        <br><br>
        <button type="submit">追加</button>
    </form>
</body>
</html>
