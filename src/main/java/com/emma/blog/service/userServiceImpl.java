package com.emma.blog.service;

import com.emma.blog.entity.User;
import com.emma.blog.mapper.UserMapper;
import com.emma.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 0:19
 */
@Service
public class userServiceImpl implements userService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code1(password));
        return user;
    }

}
