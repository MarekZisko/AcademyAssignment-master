package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.NoCommentArticle;
import sk.ness.academy.dto.dao.ArticleDAO;
import sk.ness.academy.domain.Article;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public Comment findCommentById(final Integer commentId) {
    return this.articleDAO.findCommentById(commentId);
  }
  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public List<NoCommentArticle> findAllArticles() {
    return articleDAO.findAllArticles();
  }

  @Override
  public List<NoCommentArticle> findAllArticlesWithText(String searchText) {
    return articleDAO.findAllArticlesWithText(searchText);
  }

  @Override
  public List<Comment> findAllComments() {
    return this.articleDAO.findAllComments();
  }
  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void createComment(final Comment comment) {
    this.articleDAO.persist(comment);
  }



  @Override
  public void deleteById(final Integer articleId) {
    this.articleDAO.deleteById(articleId);
  }

  @Override
  public void deleteCommentById(final Integer commentId) {
    this.articleDAO.deleteCommentById(commentId);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      List<Article>articles = List.of(mapper.readValue(jsonArticles, Article[].class));
      articles.forEach(a -> articleDAO.persist(a));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
   // throw new UnsupportedOperationException("Article ingesting not implemented.");
  }

}
