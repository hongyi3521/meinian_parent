package com.hongyi.service;

import com.hongyi.pojo.User;

public interface UserService {
    User findUserByUsername(String username);
}
