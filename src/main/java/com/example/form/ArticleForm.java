package com.example.form;

import java.util.List;

import com.example.domain.Comment;

/**
 * 記事の登録時に使用するフォームクラス.
 * 
 * @author nakamuratomoya
 *
 */
public class ArticleForm {

	/** 投稿者名 */
	private String name;

	/** 投稿内容 */
	private String content;

	/** コメントリスト */
	private List<Comment> commentList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + ", commentList=" + commentList + "]";
	}

}
