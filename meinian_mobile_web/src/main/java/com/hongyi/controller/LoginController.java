package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.constant.RedisMessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Member;
import com.hongyi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map){
        // 1.取出手机号和验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        // 2.根据手机号，取出存入redis的验证码
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(redisCode == null || !redisCode.equals(validateCode)){
            // 验证码输入错误
            return Result.error().message(MessageConstant.VALIDATECODE_ERROR);
        }else{
            // 验证码输入正确就验证他是否是会员
            Member member = memberService.findByTelephone(telephone);
            if(member == null){
                // 当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            // 3：:登录成功
            // 写入Cookie，用于用户免登录
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            // 设置cookie的有效路径，Cookie默认的有效路径：上下文路径【/+项目名】
            cookie.setPath("/"); // 这里设置为上下文路径下都有效
            cookie.setMaxAge(60*60*24*30);// 有效期30天（单位秒）
            response.addCookie(cookie);
            return Result.ok().message(MessageConstant.LOGIN_SUCCESS);
        }
    }
}
