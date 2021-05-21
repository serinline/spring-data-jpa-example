package com.example.datajpaexample.repository;

import com.example.datajpaexample.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByPublished(boolean published);

    List<Article> findByTitleContaining(String title);
}