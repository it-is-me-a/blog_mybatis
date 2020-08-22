package com.emma.blog.controller.adminController;

import com.emma.blog.MyException.NotFoundException;
import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Type;
import com.emma.blog.service.blogService;
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

import java.util.List;


/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:31
 */
@Controller
@RequestMapping("/admin")
public class typeController {

    @Autowired
    private typeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){

        PageHelper.startPage(pageNum,10,true);
        Page<Type> blogs = typeService.listType();
        PageInfo<Type> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        return "admin/types";
    }

    //新增一个分类
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type_input";
    }

    //编辑type:现在页面显示之前的type
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type_input";
    }

    //提交一个分类
    @PostMapping("/types")  //对Type校验，不合格就放入result中
    public String post(@Validated Type type,
                       BindingResult result,
                       RedirectAttributes attributes){

        Type type1 = typeService.getTypeBytypeName(type.getTypeName());
        if(type1 != null){
            //已经存在
            result.rejectValue("typeName","typeNameError","分类名称不能重复");
        }

        if(result.hasErrors()){
            return "admin/type_input";
        }

        Type t = typeService.saveType(type);

        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    //编辑type
    @PostMapping("/types/{id}")   //@Valid代表要校验这个对象;BindingResult接受校验后结果,而且BindingResult必须要与前一个要校验的紧挨着
    public String editPost(@Validated Type type,
                       BindingResult result,
                       @PathVariable Integer id,
                       RedirectAttributes attributes){
        //校验
        Type type1 = typeService.getTypeBytypeName(type.getTypeName());
        if(type1 != null){
            //已经存在
            result.rejectValue("typeName","typeNameError","分类名称不能重复");
        }
        if(result.hasErrors()){
            return "admin/type_input";
        }

        Type t = typeService.updateType(id,type);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    //删除type
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
