<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                <button type="button" onclick="removeIngredient(this)">削除</button>
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
                <button type="button" onclick="removeStep(this)">削除</button>
            `;
            document.getElementById('steps').appendChild(stepDiv);
        }

        function removeIngredient(button) {
            button.parentNode.remove();
        }

        function removeStep(button) {
            button.parentNode.remove();
        }

        function fillTestData() {
            document.getElementById("recipeName").value = "テストレシピ";
            document.getElementById("recipeSummary").value = "これはテスト用のレシピです。";
            document.getElementById("category").value = "和食";

            let ingredientInputs = document.querySelectorAll("#ingredients div");
            if (ingredientInputs.length === 1) {
                ingredientInputs[0].querySelector("input[name='ingredientNames']").value = "鶏肉";
                ingredientInputs[0].querySelector("input[name='amounts']").value = "200";
                ingredientInputs[0].querySelector("input[name='units']").value = "g";
            }

            let stepInputs = document.querySelectorAll("#steps div");
            if (stepInputs.length === 1) {
                stepInputs[0].querySelector("textarea[name='stepDetails']").value = "鶏肉を一口大に切る。";
                stepInputs[0].querySelector("textarea[name='points']").value = "なるべく均等な大きさにする。";
            }
        }
    </script>
</head>
<body>
    <h1>レシピ追加</h1>
    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>

    <form action="/add" method="post">
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
                <button type="button" onclick="removeIngredient(this)">削除</button>
                <button type="button" onclick="addIngredient()">材料追加</button>
            </div>
        </div>

        <h2>工程</h2>
        <div id="steps">
            <div>
                <label for="stepDetail">工程内容:</label>
                <textarea name="stepDetails" required></textarea>
                <label for="point">ポイント:</label>
                <textarea name="points"></textarea>
                <button type="button" onclick="removeStep(this)">削除</button>
            </div>
        </div>
        <button type="button" onclick="addStep()">工程追加</button>

        <br><br>
        <button type="submit">追加</button>
        <button type="button" onclick="fillTestData()">テストデータ入力</button>
        <a href="/" style="text-decoration: none; margin-left: 10px;"><button type="button">戻る</button></a>
    </form>
</body>
</html>
