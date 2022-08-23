package com.blog.service;

import com.blog.pojo.User;

/**
 * @author 31676
 */
public interface UserService {

    User getUserByName(String userName);

    int register(String userName, String password);

    boolean login(String userName, String password);

    Boolean rePassword(String userName, String password, String rePassword);
}
