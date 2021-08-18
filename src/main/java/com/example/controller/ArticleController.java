package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事情報を操作するコントローラー.
 * 
 * @author nakamuratomoya
 *
 */
@Controller
@RequestMapping("/Article")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository  commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 記事を一覧表示する.
	 * 
	 * @param model requestスコープ
	 * @return　記事一覧
	 */
	@RequestMapping("")
	public String index(Model model) {
		
		List<Article> articleList = articleRepository.findAll();
		
		for(int i = 0 ; i < articleList.size() ; i++) {
		  Article article = articleList.get(i);
		  List<Comment> commentList = commentRepository.findByArticleId(article.getId());
		  article.setCommentList(commentList);
		}
		
		model.addAttribute("articleList", articleList);
		
		return "article-comment";
	}
	
	/**
	 * 記事を投稿する.
	 * 
	 * @param articleForm　フォームクラス
	 * @param model　requestスコープ
	 * @return　記事投稿
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm articleForm, Model model) {
		
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		
		return "redirect:/Article";
		
	}
	
//	/**
//	 * @return
//	 */
//	@RequestMapping("/insertComment")
//	public String insertComment() {
//		
//	}
	

}
