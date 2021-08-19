package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルを操作するリポジトリ.
 * 
 * @author nakamuratomoya
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	//初級課題で使用した
	/**
	 * Articleオブジェクトを生成するローマッパー.<br>
	 * rsでテーブル1行文のレコードを取得
	 */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {

		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;

	};

	/**
	 * Articleオブジェクトを生成するリサルトセットエクストレーター.<br>
	 * rsでテーブル全行を取得
	 */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {

		//記事の入る空のarticleListを生成
		List<Article> articleList = new ArrayList<>();
		
		//コメントの入る空のcommentListを生成
		List<Comment> commentList = null;

		// 前の行の記事IDと比較する記事IDを初期値とし変数に入れてる
		int beforeArticleId = 0;

		while (rs.next()) {

			// 現在検索されている記事IDを取得
			int nowArticleId = rs.getInt("id");

			// 現在の記事IDと前の記事IDが違う場合はArticleオブジェクトを生成
			if (nowArticleId != beforeArticleId) {
				Article article = new Article();
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				
				// 空のコメントリストを作成しArticleオブジェクトにセットしておく
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				
				// コメントがセットされていない状態のArticleオブジェクトをarticleListオブジェクトにadd
				articleList.add(article);
			}
			
			// 記事だけあってコメントがない場合はCommentオブジェクトは作らない（コメントが0でない場合はtrue）
			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				
				// コメントをarticleオブジェクト内にセットされているcommentListに直接addしている(参照型なのでこのようなことができる)
				commentList.add(comment);
			}

			// 現在の記事IDを前の記事IDを入れる退避IDに格納
			beforeArticleId = nowArticleId;

		}

		return articleList;

	};

	/**
	 * 記事を全件検索する.
	 * 
	 * @return 記事一覧
	 */
	public List<Article> findAll() {

//		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC ;";
		String sql = "SELECT a.id, a.name, a.content, com.id AS com_id, com.name AS com_name, com.content AS com_content, com.article_id FROM articles AS a LEFT OUTER JOIN comments AS com ON a.id = com.article_id ORDER BY a.id DESC, com.id ASC ;";

		List<Article> articleList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;

	}

	/**
	 * 記事を追加する.
	 * 
	 * @param article 記事情報
	 */
	public void insert(Article article) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String insertSql = "INSERT INTO articles (name, content) VALUES (:name, :content) ;";

		template.update(insertSql, param);
	}

	/**
	 * 該当のIDの記事を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(int id) {

		String deleteSql = "DELETE FROM articles WHERE id=:id ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(deleteSql, param);

	}

}
