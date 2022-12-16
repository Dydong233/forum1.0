package com.dydong.service;

import com.dydong.pojo.Comment;

import java.util.List;

public interface CommentService {
    //通过aid查询评论
    List<Comment> queryByAid(int aid);
    //通过author删除评论
    int deleteByAuthor(String author);
    //添加评论
    int addComment(Comment comment);
}
