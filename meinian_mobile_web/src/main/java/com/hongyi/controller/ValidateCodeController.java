package com.hongyi.controller;

import com.hongyi.constant.MessageConstant;
import com.hongyi.constant.RedisMessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.utils.SMSUtils;
import com.hongyi.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    // 引入redis的客户端工具
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        // 利用工具类生成4位数数字
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        // 发送短信
        try {
            SMSUtils.sendShortMessage(telephone, code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // 验证码发送失败
            return Result.error().message(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 将验证码存入redis，之后用于验证
        System.out.println("发送的手机验证码为：" + code);
        // 使用String数据类型 setex  <key>  <过期时间>   <value>
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,
                5 * 60, code.toString());
        return Result.ok().message(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    // 手机快速登录时发送手机验证码
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        // 利用工具类生成4位数数字
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        // 发送短信
        try {
            SMSUtils.sendShortMessage(telephone, code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // 验证码发送失败
            return Result.error().message(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 将验证码存入redis，之后用于验证
        System.out.println("发送的手机验证码为：" + code);
        // 使用String数据类型 setex  <key>  <过期时间>   <value>
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,
                5 * 60, code.toString());
        return Result.ok().message(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
