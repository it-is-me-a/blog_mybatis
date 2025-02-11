package com.emma.blog.service;

import com.emma.blog.entity.Comment;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 06 - 02 - 11:19
 */
public interface commentService {

    List<Comment> listCommentByBlogId(Integer blogId);

    Comment saveComment(Comment comment);
}
