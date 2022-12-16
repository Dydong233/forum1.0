package com.dydong.service;

import com.dydong.mapper.ArticleMapper;
import com.dydong.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> queryArticles() {
        return articleMapper.queryArticles();
    }

    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public Article getArticleById(int id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public List<Article> getArticleByAuthor(String author) {
        return articleMapper.getArticleByAuthor(author);
    }

    @Override
    public int deleteArticleById(int id) {
        return articleMapper.deleteArticleById(id);
    }

    @Override
    public int deleteArticleByAuthor(String author) {
        return articleMapper.deleteArticleByAuthor(author);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }
}
