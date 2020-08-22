package com.emma.blog.service;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Emma
 * @create 2020 - 05 - 25 - 23:49
 */
public interface blogService {

    Blog saveBlog(Blog blog);

    Blog getBlog(Integer id);

    Blog getAndConvert(Integer id);

    void saveBlogAndType(Integer blogId, Integer tagId);

    /* 后台的搜索查询*/
    Page<Blog> listBlog(BlogQuery blog);

    /*按照分页的方式取出所有的blog*/
    Page<Blog> listBlog();

    //-----
    /*前端的搜索语句查询*/
    com.github.pagehelper.Page<Blog> listBlog(String query);

    /*按照tagId搜索，取消这个方法*/
    Page<Blog> listBlogByTag(Integer tagId);

    List<Blog> listBlogTop(Integer index);/*拿到排名前几的文章，按照views的倒序进行排列*/

    List<Blog> listBlogNew(Integer index);/*拿到排名前几的文章，按照日期的倒序进行排列*/

    List<String> blogCreateTime();/*查出所有的博客的创建时间*/

    Map<String,List<Blog>> archivesBlog();/*查出对应日期的博客，这里的日期也是从数据库查出来的*/

    List<Blog> archivesBlog(String time);/*查出对应日期的博客，这里的日期是自己输入的*/

    Integer countBlog();/*计算总共有多少博客*/

    Blog updateBlog(Integer id, Blog blog);

    void deleteBlog(Integer id);

    void deleteBlogAndTag(Integer blogId);

}
