package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    //获取当前登录用户的用户名
    @RequestMapping("/getUsername")
    public Result getUsername()throws Exception{
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Result.ok().message(MessageConstant.GET_USERNAME_SUCCESS).data("user",user);
        }catch (Exception e){
            return Result.error().message(MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
