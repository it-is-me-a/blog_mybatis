package com.emma.blog.controller;

import com.emma.blog.entity.Comment;
import com.emma.blog.entity.User;
import com.emma.blog.service.blogService;
import com.emma.blog.service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Emma
 * @create 2020 - 06 - 02 - 11:14
 */
@Controller
public class commentController {

    @Autowired
    private commentService commentService;
    @Autowired
    private blogService blogService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId,
                           Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Integer blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
       // System.out.println(user.toString());
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
