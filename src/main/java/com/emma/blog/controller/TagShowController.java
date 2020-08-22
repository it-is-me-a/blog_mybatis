package com.emma.blog.controller;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 06 - 03 - 19:24
 */
@Controller
public class TagShowController {

    @Autowired
    private tagService tagService;

    @Autowired
    private blogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @PathVariable Integer id,
                        Model model){
        List<Tag> tags = tagService.listTag();//listTagTop(1000);
        if(id == -1){
            id = tags.get(0).getId();
        }

        PageHelper.startPage(pageNum,10,true);
        List<Blog> blogs = blogService.listBlogByTag(id);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
