package com.emma.blog.service;

import com.emma.blog.MyException.NotFoundException;
import com.emma.blog.entity.Blog;
import com.emma.blog.entity.Tag;
import com.emma.blog.mapper.TagMapper;
import com.emma.blog.util.MyBeanUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 25 - 16:07
 */
@Service
public class tagServiceImpl implements tagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        tagMapper.insertSelective(tag);
        return tagMapper.selectByPrimaryKey(tag.getId());
    }

    @Transactional
    @Override
    public Tag getTag(Integer id) {
        return tagMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag() {
        return tagMapper.findAll();
    }

    //一次性获取多个tag select * from t_tag where id in (21,22);
    @Override
    public List<Tag> listTag(String ids) {
        return tagMapper.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer index) {
        /*Pageable pageable = PageRequest.of(0,index, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagRepository.findTop(pageable);*/
        return null;
    }

    @Override
    public List<Tag> listTagByBlogId(Integer blogId) {
        return tagMapper.listTagByBlogId(blogId);
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

    @Transactional
    @Override
    public Tag updateTag(Integer id, Tag tag) {
        Tag tag1 = tagMapper.selectByPrimaryKey(id);
        if(tag1 == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,tag1, MyBeanUtils.getNullPropertyNames(tag));//将tag的内容覆盖掉tag1的内容
        tagMapper.updateByPrimaryKeySelective(tag1);
        return tagMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void deleteTag(Integer id) {
        tagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBlogAndTag(Integer tagId) {
        tagMapper.deleteBlogAndTag(tagId);
    }

    @Transactional
    @Override
    public Tag getTagBytagName(String tagName) {
        return tagMapper.findByTagName(tagName);
    }
}
