package com.example.datajpaexample.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "articles")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    public Article(String title, String description, boolean published){
        this.title = title;
        this.description = description;
        this.published = published;
    }

}
