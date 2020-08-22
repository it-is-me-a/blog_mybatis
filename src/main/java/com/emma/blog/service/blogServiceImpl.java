package com.emma.blog.service;

import com.emma.blog.mapper.BlogMapper;
import com.emma.blog.MyException.NotFoundException;
import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Type;
import com.emma.blog.util.MarkdownUtils;
import com.emma.blog.util.MyBeanUtils;
import com.emma.blog.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Emma
 * @create 2020 - 05 - 26 - 0:00
 */
@Service
public class blogServiceImpl implements blogService {

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            blog.setRecommend(true);//设置推荐为true，这样前端就不用加上这个了（个人偏好设置）
        } else{
            //不是新增
            blog.setUpdateTime(new Date());
        }

        blogMapper.insertBlog(blog);
        return blogMapper.getBlogById(blog.getId());
    }

    @Override
    public void saveBlogAndType(Integer blogId, Integer tagId) {
        blogMapper.saveBlogAndTag(blogId, tagId);
    }


    @Override
    public Blog getBlog(Integer id) {
        return blogMapper.getBlogById(id);
    }

    /*获取博客，并以markdown的形式展示*/
    @Transactional
    @Override
    public Blog getAndConvert(Integer id) {
        Blog blog = blogMapper.getBlogById(id);
        if(blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);/*复制的原因是为了不改变原有的blog在数据库中的内容*/
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogMapper.updateViews(id);/*增加一次浏览量*/

        return b;
    }


    /*因为分页查询的时候，可能会传入type，tag的信息，所以要想实现动态的组合查询
     * 首先blogMapper得继承JpaSpecificationExecutor<Blog>
     * 因为它里面有继承的动态的组合查询方法*/
    @Override
    public Page<Blog> listBlog(BlogQuery blog) {
        return blogMapper.getAllBlogsByTitleOrType(blog);
    }

    @Override
    public Page<Blog> listBlog() {
        return blogMapper.getAllBlogs();
    }


    @Override
    public com.github.pagehelper.Page<Blog> listBlog(String query) {
        return blogMapper.getAllBlogsByQuery(query);
    }

    //还没有实现
    @Override
    public Page<Blog> listBlogByTag(Integer tagId) {
        return blogMapper.listBlogByTag(tagId);
    }

    @Override
    public List<Blog> listBlogTop(Integer index) {
        return blogMapper.getAllBlogsByViewsTop(index);
    }

    @Override
    public List<Blog> listBlogNew(Integer index) {
        return blogMapper.getAllBlogsByTimeTop(index);
    }

    /*查出所有的博客的创建时间*/
    @Override
    public List<String> blogCreateTime() {
        return blogMapper.findAllCreateTime();
    }

    /*查出对应日期的博客，这里的日期也是从数据库查出来的*/
    @Override
    public Map<String, List<Blog>> archivesBlog() {
        /*先查出所有的日期*/
        List<String> times = blogMapper.findAllCreateTime();

        Map<String,List<Blog>> map = new HashMap<>();

        for(String time : times){
            map.put(time, blogMapper.findBlogByTime(time));/*根据日期将对应的博客查出来*/
        }
        return map;
    }

    /*查出对应日期的博客，这里的日期是自己输入的*/
    @Override
    public List<Blog> archivesBlog(String time) {
        return blogMapper.findBlogByTime(time);
    }

    @Override
    public Integer countBlog() {
        return blogMapper.getAllBlogsCount();
    }

    @Transactional
    @Override
    public Blog updateBlog(Integer id, Blog blog) {
        Blog blog1 = blogMapper.getBlogById(id);
        if(blog1 == null){
            //放置一个错误信息
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));//MyBeanUtils.getNullPropertyNames(blog)过滤掉属性值为空
        blog1.setUpdateTime(new Date());
        blogMapper.updateByPrimaryKeySelective(blog1);
        return blogMapper.getBlogById(id);
    }

    @Transactional
    @Override
    public void deleteBlog(Integer id) {
        blogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBlogAndTag(Integer blogId) {
        blogMapper.deleteBlogAndTag(blogId);
    }
}
