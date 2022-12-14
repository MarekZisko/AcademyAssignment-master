package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.dto.NoCommentArticle;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;

@RestController
public class BlogController {

    @Resource
    private ArticleService articleService;

    @Resource
    private AuthorService authorService;

    // ~~ Article
    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public List<NoCommentArticle> getAllArticles() {
        return this.articleService.findAllArticles();
    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
    public Article getArticle(@PathVariable final Integer articleId) {
        return this.articleService.findByID(articleId);
    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable final Integer articleId) {
        this.articleService.deleteById(articleId);
    }

    @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
    public List<NoCommentArticle> searchArticle(@PathVariable final String searchText) {
        return this.articleService.findAllArticlesWithText(searchText);
       // throw new UnsupportedOperationException("Full text search not implemented.");
    }

    @RequestMapping(value = "articles", method = RequestMethod.PUT)
    public void addArticle(@RequestBody final Article article) {
        this.articleService.createArticle(article);
    }

    // ~~ Author
    @RequestMapping(value = "authors", method = RequestMethod.GET)
    public List<Author> getAllAuthors() {
        return this.authorService.findAll();
    }

    @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
    public List<AuthorStats> authorStats() {
        return this.authorService.authorStats();
        //throw new UnsupportedOperationException("Author statistics not implemented.");
    }
    // ~~ Comment
    @RequestMapping(value = "comments", method = RequestMethod.GET)
    public List<Comment> getAllComments() {
        return this.articleService.findAllComments();
    }

    @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable final Integer commentId)

    {
        return this.articleService.findCommentById(commentId);
    }
    @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable final Integer commentId) {
        this.articleService.deleteCommentById(commentId);
    }

    @RequestMapping(value = "comment", method = RequestMethod.PUT)
    public void addComment(@RequestBody final Comment comment) {
        this.articleService.createComment(comment);
    }

}
