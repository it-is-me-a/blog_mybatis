package com.emma.blog.controller;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.entity.Type;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.typeService;
import com.emma.blog.vo.BlogQuery;
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
public class TypeShowController {

    @Autowired
    private typeService typeService;

    @Autowired
    private blogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable Integer id,
                        Model model){
        List<Type> types = typeService.listTypeTop(1000);
        if(id == -1){
            id = types.get(0).getId();/*取页面第一个type对应的blog*/
        }
        model.addAttribute("types",types);

        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        PageHelper.startPage(pageNum,10,true);
        List<Blog> blogs = blogService.listBlog(blogQuery);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
