package com.dydong.mapper;

import com.dydong.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    //通过aid查询评论
    List<Comment> queryByAid(int aid);
    //通过author删除评论
    int deleteByAuthor(String author);
    //添加评论
    int addComment(Comment comment);
}
