<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>レシピ追加</title>
<link rel="stylesheet" type="text/css" href="/css/addRecipe.css">
<script>
        function addIngredient() {
            var ingredientDiv = document.createElement('div');
            ingredientDiv.innerHTML = `
                <label for="ingredientName">ing.</label>
                <input type="text" name="ingredientNames">
                <label for="amount">amounts</label>
                <input type="text" name="amounts">
                <label for="unit">unit</label>
                <input type="text" name="units">
                <button type="button" onclick="removeIngredient(this)">削除</button>
            `;
            document.getElementById('ingredients').appendChild(ingredientDiv);
        }

        var stepCount = 2;
        function addStep() {
            var stepDiv = document.createElement('div');
            stepDiv.innerHTML = `
                <label for="stepDetail">` + stepCount + ` :</label>
                <textarea name="stepDetails"></textarea>
                <label for="point">point</label>
                <textarea name="points"></textarea>
                <button type="button" onclick="removeStep(this)">削除</button>
            `;
            document.getElementById('steps').appendChild(stepDiv);
            stepCount++;
        }

        function removeIngredient(button) {
            button.parentNode.remove();
        }

        function removeStep(button) {
            button.parentNode.remove();
        }

        function autoResizeTextarea(textarea) {
            textarea.style.height = 'auto';
            textarea.style.height = textarea.scrollHeight + 'px';
        }

        function removeEmptyFields() {
            // 空の材料フィールドを削除
            const ingredientDivs = document.querySelectorAll('#ingredients > div');
            ingredientDivs.forEach(function(div, index) {
                if (index > 0) { // 最初の1つ以外
                    const nameInput = div.querySelector('input[name="ingredientNames"]');
                    const amountInput = div.querySelector('input[name="amounts"]');
                    const unitInput = div.querySelector('input[name="units"]');
                    
                    const nameValue = nameInput ? nameInput.value.trim() : '';
                    const amountValue = amountInput ? amountInput.value.trim() : '';
                    const unitValue = unitInput ? unitInput.value.trim() : '';
                    
                    // すべて空の場合は削除
                    if (!nameValue && !amountValue && !unitValue) {
                        div.remove();
                    }
                }
            });
            
            // 空の手順フィールドを削除
            const stepDivs = document.querySelectorAll('#steps > div');
            stepDivs.forEach(function(div, index) {
                if (index > 0) { // 最初の1つ以外
                    const stepTextarea = div.querySelector('textarea[name="stepDetails"]');
                    const pointTextarea = div.querySelector('textarea[name="points"]');
                    
                    const stepValue = stepTextarea ? stepTextarea.value.trim() : '';
                    const pointValue = pointTextarea ? pointTextarea.value.trim() : '';
                    
                    // 手順が空の場合は削除（ポイントだけ入力されている場合は残す）
                    if (!stepValue) {
                        div.remove();
                    }
                }
            });
        }

        function fillTestData() {
            document.getElementById("recipeName").value = "テストレシピ";
            document.getElementById("recipeSummary").value = "これはテスト用のレシピです。";
            document.getElementById("category").value = "和食";
            document.getElementById("servings").value = "2";

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

        window.addEventListener('DOMContentLoaded', function() {
            const recipeSummary = document.getElementById('recipeSummary');
            if (recipeSummary) {
                recipeSummary.addEventListener('input', function() {
                    autoResizeTextarea(this);
                });
                // 初期表示時にも高さを調整
                autoResizeTextarea(recipeSummary);
            }
            
            // フォーム送信前に空のフィールドを削除
            const form = document.querySelector('form[action="/add"]');
            if (form) {
                form.addEventListener('submit', function(e) {
                    removeEmptyFields();
                });
            }
        });
    </script>
</head>
<body>
	<h1>Add Recipe</h1>
	<c:if test="${not empty message}">
		<p>${message}</p>
	</c:if>

	<form action="/add" method="post">
		<div class="recipe_form">
			<label for="recipeName">recipeName</label>
			<input type="text" id="recipeName" name="recipeName" required placeholder="レシピ名">
			
			<label for="recipeSummary">Summary</label>
			<textarea id="recipeSummary" name="recipeSummary" placeholder="概要"></textarea>
			
			<label for="category">category</label>
			<input type="text" id="category" name="category" placeholder="カテゴリ">
			
			<label for="servings">servings</label>
			<input type="number" id="servings" name="servings" required placeholder="何人前" min="1">
		</div>

		<h2>Ingredient</h2>
		<button type="button" onclick="addIngredient()">addIng.</button>
		<div id="ingredients">
			<div>
				<label for="ingredientName">ing.</label> <input type="text"
					name="ingredientNames" required placeholder="材料"> <label for="amount">amounts </label>
				<input type="text" name="amounts" required placeholder="量"> <label
					for="unit">unit </label> <input type="text" name="units" required placeholder="単位">
				<button type="button" onclick="removeIngredient(this)">delete</button>
			</div>
		</div>

		<h2>Step</h2>
		<button type="button" onclick="addStep()">addStep</button>
		<div id="steps">
			<div class=step_form>
				<label for="stepDetail">1 :</label>
				<textarea name="stepDetails" required placeholder="手順"></textarea>
				<label for="point">point </label>
				<textarea name="points" placeholder="ポイント"></textarea>
				<button type="button" onclick="removeStep(this)">delete</button>
			</div>
		</div>

		<br>
		<br>
		<div class=bottom_submit>
		<button type="submit">add</button>
		<button type="button" onclick="fillTestData()">TestData</button>
		<a href="/" style="text-decoration: none; margin-left: 10px;"><button
				type="button">back</button></a>
		</div>
	</form>
</body>
</html>
