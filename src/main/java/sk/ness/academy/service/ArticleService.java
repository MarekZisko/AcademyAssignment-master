package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.NoCommentArticle;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	/** Returns {@link Comment} with provided ID */
	Comment findCommentById(Integer commentId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  List<NoCommentArticle> findAllArticles();

	/** Returns all available {@link Comment}s */
	List<Comment> findAllComments();

	/** Find all {@link Article} with searched text */
	List<NoCommentArticle> findAllArticlesWithText(String searchText);

	  /** Creates new {@link Article} */
	  void createArticle(Article article);

	/** Deletes {@link Article} */
	void deleteById(Integer articleId);

	/** Deletes {@link Comment} */
	void deleteCommentById(Integer commentId);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	/** Creates new {@link Comment} */
	void createComment(Comment comment);


	}
