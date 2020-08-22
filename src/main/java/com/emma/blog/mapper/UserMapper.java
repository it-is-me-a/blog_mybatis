package com.emma.blog.mapper;

import com.emma.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /*int deleteByPrimaryKey(Integer id);*/

    /*int insert(User record);
    int insertSelective(User record);*/

    User getUserById(Integer id);

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /*int updateByPrimaryKeySelective(User record);//不为空才可以update
    int updateByPrimaryKey(User record);//无条件的update*/
}