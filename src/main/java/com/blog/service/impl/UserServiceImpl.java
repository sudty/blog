package com.blog.service.impl;

import com.blog.dao.UserMapper;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.util.PasswordUtil;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 31676
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserMapper userMapper;
    
    
    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByname(userName);
    }

    @Override
    public int register(String userName, String password) {
        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator(0,0);
        User user = new User();
        String salt = UUIDUtils.randomUUID();
        user.setUserName(userName);
        user.setPassword(PasswordUtil.sha1(password,salt));
        user.setId((int) snowFlakeGenerator.nextId());
        user.setSalt(salt);
        user.setRemark(null);
        System.out.println(user);
        return userMapper.insert(user);
    }

    @Override
    public boolean login(String userName, String password) {
        User user = userMapper.getUserByname(userName);
        return user.getPassword().equals(PasswordUtil.sha1(password,user.getSalt()));
    }

    @Override
    public Boolean rePassword(String userName, String password, String rePassword) {
        User user = userMapper.getUserByname(userName);
        if (!user.getPassword().equals(PasswordUtil.sha1(password,user.getSalt()))){
            return false;
        }
        String salt = user.getSalt();
        int updatePassword = userMapper.updatePassword(userName, PasswordUtil.sha1(rePassword,salt));
        return updatePassword > 0;
    }


}
