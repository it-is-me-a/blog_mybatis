package com.emma.blog.mapper;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Comment record);

    Comment getCommentById(Integer id);

    List<Comment> getChildCommentByParentId(Integer ParentId);

    Blog getParentCommentByCommentId(Integer id);

    /*
     按照blogId查找，并且ParentComment为空
     */
    List<Comment>  findByBlogIdAndParentCommentNull(Integer blogId);

    int updateByPrimaryKeySelective(Comment record);

}