package com.emma.blog.mapper;

import com.emma.blog.entity.Type;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    Page<Type> findAll();

    List<Type> findTypeByTop(Integer index);

    Type findByTypeName(String typeName);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}