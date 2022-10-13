package sk.ness.academy.dto.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.NoCommentArticle;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @Override
  public Comment findCommentById(final Integer commentId) {
    return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
  }

  @Override
  public void deleteById(final Integer articleId) {
    this.sessionFactory.getCurrentSession().delete(findByID(articleId));
  }


  @Override
  public void deleteCommentById(final Integer commentId) {
    this.sessionFactory.getCurrentSession().delete(findCommentById(commentId));
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
   List<Article> list = this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
   list.stream().map(a -> a.getComments());
   return list;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NoCommentArticle> findAllArticlesWithText(String searchText) {
    List<NoCommentArticle> searchTextArticle = findAllArticles();
    List<NoCommentArticle> helpList = new ArrayList<>();
    for(NoCommentArticle var : searchTextArticle)
    {
      if (var.getText().contains(searchText) || var.getAuthor().contains(searchText) || var.getTitle().contains(searchText)){
        helpList.add(var);
      }
    }
    return helpList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Comment> findAllComments() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NoCommentArticle> findAllArticles() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles")
            .addScalar("id", IntegerType.INSTANCE)
            .addScalar("title", StringType.INSTANCE)
            .addScalar("text", StringType.INSTANCE)
            .addScalar("author", StringType.INSTANCE)
            .setResultTransformer(new AliasToBeanResultTransformer(NoCommentArticle.class)).list();

  }

  @Override
  public void persist(final Article article) {
    if (article == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article doesn't exist");
    }
    if (article.getTitle() == null || article.getAuthor() == null || article.getText() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incomplete article data");
    }
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }


  @Override
  public void persist(final Comment comment) {
    if (comment == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No comment available");
    }
    if (comment.getAuthor() == null || comment.getText() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incomplete comment format");
    }
    this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
  }

}
