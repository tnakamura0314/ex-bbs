package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

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

	/**
	 * Articleオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {

		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;

	};

	/**
	 * 記事を全件検索する.
	 * 
	 * @return 記事一覧
	 */
	public List<Article> findAll() {

		String sql = "SELECT id, name, content FROM articles ORDER BY id ASC ;";

		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

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
