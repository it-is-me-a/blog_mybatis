package com.emma.blog.controller.adminController;

import com.emma.blog.entity.Tag;
import com.emma.blog.entity.Type;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.typeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:31
 */
@Controller
@RequestMapping("/admin")
public class tagController {

    @Autowired
    private tagService tagService;

    /*
    跳转到tag列表页面，分页
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       Model model){
        PageHelper.startPage(pageNum,10,true);
        Page<Tag> blogs = tagService.listTag();
        PageInfo<Tag> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        return "admin/tags";
    }

    //新增一个tag
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag_input";
    }

    //提交一个tag
    @PostMapping("/tags")   //@Valid代表要校验这个对象;BindingResult接受校验后结果
    public String post(@Validated Tag tag,
                       BindingResult result,
                       RedirectAttributes attributes){

        Tag tag1 = tagService.getTagBytagName(tag.getTagName());
        if(tag1 != null){
            //已经存在
            result.rejectValue("tagName","tagNameError","标签名称不能重复");
        }
        //校验有错误
        if(result.hasErrors()){
            return "admin/tag_input";
        }

        //没有问题，就保存新的tag
        Tag t = tagService.saveTag(tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","tag新增失败");
        }else{
            attributes.addFlashAttribute("message","tag新增成功");
        }
        return "redirect:/admin/tags";
    }

    //编辑tag:先在页面显示之前的tag
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag_input";
    }

    //编辑tag
    @PostMapping("/tags/{id}")
    public String editPost(@Validated Tag tag,
                       BindingResult result,
                       @PathVariable Integer id,
                       RedirectAttributes attributes){
        //校验
        Tag tag1 = tagService.getTagBytagName(tag.getTagName());
        if(tag1 != null){
            //已经存在
            result.rejectValue("tagName","tagNameError","标签名称不能重复");
        }
        //校验有错误
        if(result.hasErrors()){
            return "admin/tag_input";
        }

        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    //删除tag
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        tagService.deleteBlogAndTag(id);
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
