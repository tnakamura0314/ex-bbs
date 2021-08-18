package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;



/**
 * commentsテーブルを操作するリポジトリ.
 * 
 * @author nakamuratomoya
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * commentオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) ->{
	
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getNString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		
		return comment;
		
	};
	
	/**
	 * 記事ID該当のコメント情報を取得する.
	 * 
	 * @param articleId 記事ID
	 * @return　記事ID該当のコメント情報
	 */
	public List<Comment> findByArticleId(int articleId){
		
		String sql = "SELECT id, name, content, article_id FROM comments WHERE article_id=:articleId ORDER BY id DESC ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		
		return commentList;
		
	}
	
	/**
	 * コメントを追加する.
	 * 
	 * @param comment コメント情報
	 */
	public void insert(Comment comment) {
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		
		String insertSql = "INSERT INTO comments (name, content, article_id) VALUES (:name, :content, :articleId);";
		
		template.update(insertSql, param);
		
	}
	
	/**
	 * 投稿ID該当のコメントを削除する.
	 * 
	 * @param articleId　投稿ID
	 */
	public void deleteByArticleId(int articleId) {
		
		String deleteSql = "DELETE FROM comments WHERE article_id=:articleId ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		template.update(deleteSql, param);
	}

}
