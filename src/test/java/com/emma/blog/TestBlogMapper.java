package com.emma.blog;

import com.emma.blog.MyException.NotFoundException;
import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.entity.Type;
import com.emma.blog.entity.User;
import com.emma.blog.mapper.BlogMapper;
import com.emma.blog.mapper.TagMapper;
import com.emma.blog.mapper.TypeMapper;
import com.emma.blog.mapper.UserMapper;
import com.emma.blog.service.blogService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.typeService;
import com.emma.blog.service.tagService;
import com.emma.blog.service.blogService;
import com.emma.blog.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.krb5.internal.TGSRep;

import java.util.Date;
import java.util.List;

/**
 * @author Emma
 * @create 2020 - 08 - 15 - 21:06
 */
@SpringBootTest(classes = BlogMybatisApplication.class)
@RunWith(SpringRunner.class)
public class TestBlogMapper {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private typeService typeService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private tagService tagService;

    @Autowired
    private blogService blogService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test07(){
        PageHelper.startPage(1, 3,true);
        Page<Blog> blogs = blogService.listBlog();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        List<Blog> list = blogPageInfo.getList();
        for(Blog blog : list){
            System.out.println(blog.toString());
        }

        System.out.println("------------------");
        System.out.println(blogs.toString());
        System.out.println(blogPageInfo.toString());
    }

    @Test
    public void test01(){
        /*int insertBlog(Blog blog);*/
        Blog b = new Blog();
        b.setTitle("这是一个测试文件03");
        /*b.setAppreciation(false);
        b.setCommentabled(false);
        b.setContent("test,test");
        //b.setCreatTime(new Date());
        b.setFirstPicture(null);
        b.setFlag("原创");
        b.setPublished(false);
        b.setRecommend(false);
        b.setShareStatement(false);
        //b.setUpdateTime(new Date());
        //b.setViews(100);
        b.setDescription("test,test");

        User u = userMapper.getUserById(1);
        b.setUser(u);
        Type type = typeService.getType(8);
        b.setType(type);*/

        //Blog i = blogService.updateBlog(14,b);
        blogService.deleteBlog(14);
        //System.out.println("输出结果为："+i);
    }

    @Test
    public void test02(){
        /*insertSelective;*/
        Type b = new Type();
        b.setTypeName("测试");

        int i = typeMapper.insertSelective(b);
        System.out.println("输出结果为："+i);
    }

    @Test
    public void Test03(){
        Type b = new Type();
        b.setTypeName("测试02");

        Type one = typeMapper.selectByPrimaryKey(9);
        System.out.println("输出结果1为："+one);
        if(one == null){
            //没有查询出来
            throw new NotFoundException("不存在该类型");
        }
        System.out.println("复制前，one为"+one);
        BeanUtils.copyProperties(b,one);//将前者复制给后者
        System.out.println("one为"+one);
        typeMapper.updateByPrimaryKeySelective(one);
        Type t2 = typeMapper.selectByPrimaryKey(one.getId());


        //Type t = typeService.updateType(9,b);
        System.out.println("输出结果为："+t2);
    }

    @Test
    public void test04(){
        /*tagMapper.findAll();*/
        /*List<Tag> all = tagMapper.findAll();
        for(Tag t:all){

        }*/

        /*Tag tag1 = tagService.getTagBytagName*/


        Tag t = new Tag();
        t.setId(21);
        t.setTagName("花花的世界");
        Tag tag = tagService.updateTag(21, t);
        System.out.println("输出结果为："+tag);

    }

    @Test
    public void test05(){
        /*private String title;//标题
          private Integer typeId;//分类*/
        BlogQuery blog = new BlogQuery();
        blog.setTitle("这是一个测试文件");
        /*blog.setTypeId(8);*/
        System.out.println("blog"+blog);
        List<Blog> blogs = blogService.listBlog(blog);
        for(Blog b : blogs){
            System.out.println("输出结果："+b.getTitle());
        }
    }

    @Test
    public void test06(){
        /*List<Tag> findAllById(List<Integer> ids);*/
        /*public List<Tag> listTag(String ids)*/
        String ids = "21,22";
        List<Tag> tags = tagService.listTag(ids);
        for(Tag tag:tags){
            System.out.println(tag);
        }
    }
}
