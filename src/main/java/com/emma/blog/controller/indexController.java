package com.emma.blog.controller;

import com.emma.blog.entity.Blog;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.typeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 21 - 15:49
 */
@Controller
public class indexController {

    @Autowired
    private blogService blogService;
    @Autowired
    private typeService typeService;

    @GetMapping("/main")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){
        /*拿到blog,type,tag的数据显示在首页*/
        PageHelper.startPage(pageNum, 10, true);//true表示统计总数
        List<Blog> blogs = blogService.listBlog();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        /*拿到排名前8的type展示在对应区域*/
        model.addAttribute("types",typeService.listTypeTop(8));

        /*拿到排名前5的文章展示在对应区域*/
        model.addAttribute("recommendBlogs",blogService.listBlogTop(5));
        /*拿到最新的前5的文章展示在最新文章*/
        model.addAttribute("NewBlogs",blogService.listBlogNew(5));
        return "index";
    }

    @GetMapping({"/","/index"})
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam String query,
                         Model model){
        PageHelper.startPage(pageNum,10,true);
        List<Blog> blogs = blogService.listBlog(query);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
