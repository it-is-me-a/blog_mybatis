package com.emma.blog.controller.adminController;

import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.entity.User;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.typeService;
import com.emma.blog.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Emma
 * @create 2020 - 05 - 23 - 17:08
 */
@Controller
@RequestMapping("/admin")
public class blogController {

    private static final String INPUT="admin/blog_input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private blogService blogService;
    @Autowired
    private typeService typeService;
    @Autowired
    private tagService tagService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        BlogQuery blog, Model model){

        model.addAttribute("types",typeService.listType());

        PageHelper.startPage(pageNum,10,true);
        String title = blog.getTitle();
        if(title != null){
            title = "%"+title+"%";
        }else{
            title="%%";
        }
        blog.setTitle(title);
        Page<Blog> blogs = blogService.listBlog(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);
        return LIST;
    }

    /*Ajax 局部刷新*/
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         BlogQuery blog, Model model){

        PageHelper.startPage(pageNum,10,true);
        String title = blog.getTitle();
        if(title != null){
            title = "%"+title+"%";
        }else{
            title="%%";
        }
        blog.setTitle(title);
        Page<Blog> blogs = blogService.listBlog(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("page",blogPageInfo);

        return "admin/blogs :: blogList"; /*返回blogs页面的blogList片段*/
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);

        /*初始化一个blog*/
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        setTypeAndTag(model);
        Blog b = blogService.getBlog(id);/*分为两步：getBlog 以及 getTag*/
        b.init();//将blog中的tag集合转为字符串，然后放入blog的tagIds字段中
        model.addAttribute("blog", b);
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        /*初始化分类和标签*/
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog,
                       RedirectAttributes attributes,
                       HttpSession session){

        blog.setUser((User) session.getAttribute("user"));/*拿到当前用户*/

        blog.setType(typeService.getType(blog.getType().getId()));

        /*拿到前端传进来的tags，并进行处理.如果有新的tags，就进行保存*/
        String ids = blog.getTagIds();
        ids = handleTags(ids);//保存新的tag，并将blog所对应的所有的tagId保存到ids中
        blog.setTagIds(ids);

        //重新set tag
        blog.setTags(tagService.listTag());

        Blog b;
        if(blog.getId() == null){
            //saveBlog，
            b = blogService.saveBlog(blog);
            //insert t_blog_tags
            List<Integer> tagIds = convertToList(ids);
            for(Integer tagId : tagIds){
                blogService.saveBlogAndType(b.getId(),tagId);
            }
        }else{
            //1. update blog
            b = blogService.updateBlog(blog.getId(),blog);
            //2. 删除 t_blog_tags
            blogService.deleteBlogAndTag(b.getId());
            //3. insert t_blog_tags
            List<Integer> tagIds = convertToList(ids);
            for(Integer tagId : tagIds){
                blogService.saveBlogAndType(b.getId(),tagId);
            }
        }

        if(b == null){
            //新增失败
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }



    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        blogService.deleteBlogAndTag(id);
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

    /*处理传进来的tag,如果tag有新的，进行保存*/
    private String handleTags(String ids){
        boolean num = false;//用来记录是否有新的tag,有的话是true

        /*分割字符串*/
        String[] idarray = ids.split(",");
        for(int i =0; i<idarray.length; i++){
            //判断此string是否为数字，不为数字代表有新的tag，且string是新tag的tagname
            boolean b = StringUtils.isNumeric(idarray[i]);
            if(b == false){
                //不是数字，则添加新tag，并将新tag的id放入数组
                Tag t = tagService.saveTag(new Tag(idarray[i]));
                Integer idnew = t.getId();
                idarray[i] = idnew.toString();
                num = true;
            }
        }
        return num == true? tagsToIds(idarray) : ids;
    }
    /*将数组转为字符串*/
    private String tagsToIds(String[] idarray) {
        StringBuffer ids = new StringBuffer();
        boolean flag = false;
        for (String s : idarray) {
            if (flag) {
                ids.append(",");
            } else {
                flag = true;
            }
            ids.append(s);
        }
        return ids.toString();
    }

    //字符串转集合
    private List<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }


}
