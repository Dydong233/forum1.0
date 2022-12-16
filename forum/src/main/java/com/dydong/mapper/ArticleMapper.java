package com.dydong.mapper;

import com.dydong.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //查询所有文章
    List<Article> queryArticles();
    //新增一个文章
    int addArticle(Article article);
    //根据id查询文章
    Article getArticleById(int id);
    //根据作者查询文章
    List<Article> getArticleByAuthor(String author);
    //通过id删除文章
    int deleteArticleById(int id);
    //通过Author删除文章
    int deleteArticleByAuthor(String author);
    //通过id修改文章
    void updateArticle(Article article);
}
