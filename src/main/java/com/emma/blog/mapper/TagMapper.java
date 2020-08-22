package com.emma.blog.mapper;

import com.emma.blog.entity.Tag;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    void deleteBlogAndTag(Integer tagId);

    int insert(Tag record);

    int insertSelective(Tag record);

    int CountTag();

    Tag selectByPrimaryKey(Integer id);

    List<Tag> listTagByBlogId(Integer blogId);

    List<Tag> findAllById(List<Integer> ids);

    Page<Tag> findAll();

    Tag findByTagName(String tagName);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}