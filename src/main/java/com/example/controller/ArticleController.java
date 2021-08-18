package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

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
	private ArticleRepository repository;
	
	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	
	/**
	 * 記事を一覧表示する.
	 * 
	 * @param model requestスコープ
	 * @return　記事一覧
	 */
	@RequestMapping("")
	public String index(ArticleForm articleForm, Model model) {
		
		List<Article> articleList = repository.findAll();
		BeanUtils.copyProperties(articleForm, articleList);
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
		repository.insert(article);
		
		return "redirect:/Article";
		
	}
	

}
