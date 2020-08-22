package com.emma.blog.entity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer id;

    @NotBlank(message="标签名称不能为空") //后台检测message不为空
    private String tagName;

    //@ManyToMany(mappedBy ="tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }


    public Tag(String tagName){
        this.tagName=tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}