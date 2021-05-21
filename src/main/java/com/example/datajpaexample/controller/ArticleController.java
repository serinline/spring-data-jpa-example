package com.example.datajpaexample.controller;

import com.example.datajpaexample.model.Article;
import com.example.datajpaexample.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ArticleController {

    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) String title) {
        try {
            List<Article> articles = new ArrayList<>();

            if (title == null) {
                articles.addAll(articleRepository.findAll());
            } else {
                articles.addAll(articleRepository.findByTitleContaining(title));
            }

            return articles.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : ResponseEntity.ok(articles);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
        Optional<Article> tutorialData = articleRepository.findById(id);

        return tutorialData.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            Article newArticle = articleRepository
                    .save(new Article(article.getTitle(), article.getDescription(), false));
            return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article) {
        Optional<Article> articleData = articleRepository.findById(id);

        if (articleData.isPresent()) {
            Article currentArticleToUpdate = articleData.get();
            currentArticleToUpdate.setTitle(article.getTitle());
            currentArticleToUpdate.setDescription(article.getDescription());
            currentArticleToUpdate.setPublished(article.isPublished());
            return new ResponseEntity<>(articleRepository.save(currentArticleToUpdate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {
        try {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/articles")
    public ResponseEntity<HttpStatus> deleteAllArticles() {
        try {
            articleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/articles/published")
    public ResponseEntity<List<Article>> findByPublished() {
        try {
            List<Article> articles = articleRepository.findByPublished(true);

            return articles.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : ResponseEntity.ok(articles);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
