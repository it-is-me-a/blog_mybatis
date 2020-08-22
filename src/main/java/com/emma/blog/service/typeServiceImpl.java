package com.emma.blog.service;


import com.emma.blog.MyException.NotFoundException;
import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Type;
import com.emma.blog.mapper.BlogMapper;
import com.emma.blog.mapper.TypeMapper;
import com.emma.blog.util.MyBeanUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:19
 */
@Service
public class typeServiceImpl implements typeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Transactional  //放在事务里面
    @Override
    public Type saveType(Type type) {
        typeMapper.insertSelective(type);
        return typeMapper.selectByPrimaryKey(type.getId());
    }

    @Transactional
    @Override
    public Type getType(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public Page<Type> listType() {
        return typeMapper.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer index) {
        //1.拿到排好序的type
        List<Type> types = typeMapper.findTypeByTop(index);
        //2.拿到type对应的blog
        for(Type ty : types){
            List<Blog> blogs = blogMapper.getBlogsByTypeId(ty.getId());
            ty.setBlogs(blogs);
        }
        return types;
    }

    @Transactional
    @Override
    public Type updateType(Integer id, Type type) {
        Type one = typeMapper.selectByPrimaryKey(id);
        if(one == null){
            //没有查询出来
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,one, MyBeanUtils.getNullPropertyNames(type));//将type的内容复制到one中

        typeMapper.updateByPrimaryKeySelective(one);
        return typeMapper.selectByPrimaryKey(one.getId());
    }

    @Transactional
    @Override
    public void deleteType(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Type getTypeBytypeName(String typeName) {
        return typeMapper.findByTypeName(typeName);
    }
}
