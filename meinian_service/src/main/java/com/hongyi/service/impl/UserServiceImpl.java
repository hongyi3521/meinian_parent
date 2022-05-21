package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyi.dao.UserDao;
import com.hongyi.pojo.User;
import com.hongyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }
}
