package com.emma.blog.service;

import com.emma.blog.entity.User;

/**
 * @author Emma
 * @create 2020 - 05 - 22 - 23:56
 */
public interface userService {

    User checkUser(String username, String password);

    //更新博客访客人数
    /*Integer updateuserPrsonalviews(Integer index);*/

}
