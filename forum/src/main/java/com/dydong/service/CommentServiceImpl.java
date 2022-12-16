package com.dydong.service;

import com.dydong.mapper.CommentMapper;
import com.dydong.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> queryByAid(int aid) {
        return commentMapper.queryByAid(aid);
    }

    @Override
    public int deleteByAuthor(String author) {
        return commentMapper.deleteByAuthor(author);
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }
}
