package com.emma.blog.vo;

/**
 这个实体类的作用是用来保存Blogs页面的查询条件
 按照 标题 或者 分类来查询 博客
 */
public class BlogQuery {
    private String title;//标题
    private Integer typeId;//分类

    public BlogQuery() {
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

}
