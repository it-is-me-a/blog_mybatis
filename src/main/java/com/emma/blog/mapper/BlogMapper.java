package com.emma.blog.mapper;

import com.emma.blog.entity.Blog;
import com.emma.blog.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BlogMapper {

    //单表
    int deleteByPrimaryKey(Integer id);

    void deleteBlogAndTag(Integer blogId);

    int insertBlog(Blog blog);

    void saveBlogAndTag(@Param("blogId") Integer blogId, @Param("tagId") Integer tagId);

    Blog getBlogById(Integer id);/*拿到全部Blog，包括user,type，tag，comment；用于blog页面的展示*/

    Integer getAllBlogsCount();

    /*Page<Blog> getAllBlogs(); 用于index页面的展示，只拿 blog,user,index*/
    Page<Blog> getAllBlogs();

    Page<Blog> listBlogByTag(Integer tagId);

    /*Blog listCommentByBlogId(Integer blogId);*/

    List<Blog> getAllBlogsByViewsTop(Integer index);//拿到views排名前index的文章

    List<Blog> getAllBlogsByTimeTop(Integer index);

    List<Blog> getBlogsByTypeId(Integer TypeId);

    List<String> findAllCreateTime();

    List<Blog> findBlogByTime(String time);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    int updateViews(Integer id); //更新views

    //多表
    Page<Blog> getAllBlogsByTitleOrType(BlogQuery blog);

    com.github.pagehelper.Page<Blog> getAllBlogsByQuery(String query);





}