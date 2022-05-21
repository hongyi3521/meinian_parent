package com.hongyi.dao;

import com.hongyi.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);
}
