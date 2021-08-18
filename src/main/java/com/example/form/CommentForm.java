package com.example.form;

/**
 * コメント投稿時に使用するフォームクラス.
 * 
 * @author nakamuratomoya
 *
 */
public class CommentForm {

	/** コメント者名 */
	private String name;

	/** コメント内容 */
	private String content;

	/** 投稿ID */
	private Integer articleId;

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

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "CommentForm [name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
	}

}
