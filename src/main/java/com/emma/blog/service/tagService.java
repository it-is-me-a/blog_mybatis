package com.emma.blog.service;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.github.pagehelper.Page;

import java.util.List;

/**
 分类
 */
public interface tagService {

    //保存一个tag
    Tag saveTag(Tag tag);

    //查询
    Tag getTag(Integer id);

    //分页查询
    Page<Tag> listTag();

    List<Tag> listTag(String ids);//一次性获取多个tag select * from t_tag where id in (21,22);

    List<Tag> listTagTop(Integer index);

    List<Tag> listTagByBlogId(Integer blogId);

    //修改
    Tag updateTag(Integer id, Tag tag);

    //删除
    void deleteTag(Integer id);

    void deleteBlogAndTag(Integer tagId);

    //通过名称来查询一个tag
    Tag getTagBytagName(String tagName);
}
