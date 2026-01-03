# レシピ管理アプリケーション

## 概要
このアプリケーションは、家庭で使える料理レシピとその材料を管理するためのWebアプリケーションです。
レシピの登録から検索まで、料理に関する情報を簡単に管理できます。

## 機能
- レシピの登録、編集、削除
- 材料の管理（分量、単位を含む）
- レシピの検索機能
  - 材料名による検索
  - レシピ名による検索
- カテゴリー別のレシピ一覧表示

## 技術スタック
### バックエンド
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Lombok

### フロントエンド
- JSP (JavaServer Pages)
- JSTL
- Bootstrap 5
- JavaScript

## データベース設計
### レシピテーブル (recipes)
| カラム名 | 型 | 説明 |
|----------|------|------|
| recipe_id | BIGINT | 主キー、自動採番 |
| recipe_name | VARCHAR | レシピ名 |
| description | TEXT | レシピの説明 |
| cooking_time | INTEGER | 調理時間（分） |
| servings | INTEGER | 何人分 |
| created_at | TIMESTAMP | 作成日時 |
| updated_at | TIMESTAMP | 更新日時 |

### 材料テーブル (ingredienstable)
| カラム名 | 型 | 説明 |
|----------|------|------|
| ingredient_id | BIGINT | 主キー、自動採番 |
| recipe_id | BIGINT | 外部キー（レシピテーブルへの参照） |
| ingredient_name | VARCHAR | 材料名 |
| amount | DOUBLE | 分量 |
| unit | VARCHAR | 単位（g、ml、個など） |

## セットアップ方法
1. 必要条件
   - JDK 17以上
   - Maven 3.8以上

2. リポジトリのクローン
```bash
git clone [リポジトリURL]
```

3. アプリケーションのビルド
```bash
./mvnw clean install
```

4. アプリケーションの起動
```bash
./mvnw spring-boot:run
```

5. ブラウザでアクセス
```
http://localhost:8080
```

## 主要な画面
- トップページ: `/`
- レシピ一覧: `/recipes`
- レシピ詳細: `/recipes/{id}`
- レシピ検索: `/search`
- レシピ登録: `/recipes/new`
- レシピ編集: `/recipes/{id}/edit`

## 開発環境のセットアップ
1. IDE（Eclipse/IntelliJ IDEA）でプロジェクトを開く
2. Lombokプラグインをインストール
3. プロジェクトをMavenプロジェクトとしてインポート
4. アプリケーションを実行

## 貢献方法
1. このリポジトリをフォーク
2. 新しいブランチを作成 (`git checkout -b feature/amazing-feature`)
3. 変更をコミット (`git commit -m 'Add some amazing feature'`)
4. ブランチをプッシュ (`git push origin feature/amazing-feature`)
5. プルリクエストを作成

## ライセンス
MIT License

## 作者
[Ayane]

## サポート
問題や質問がある場合は、Issueを作成してください。
