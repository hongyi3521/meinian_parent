package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.constant.RedisMessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Order;
import com.hongyi.service.OrderService;
import com.hongyi.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map map) {
        // 1.获取手机号
        String telephone = (String) map.get("telephone");
        // 2.取得传递过来的验证码
        String validateCode = (String) map.get("validateCode");
        // 3.从redis取出生成的验证码
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (validateCode == null || !redisCode.equals(validateCode)) {
            // 不对就直接返回
            return Result.error().message(MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        // 默认是微信预约
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.submitOrder(map);
            if (result.isFlag()) {
                //预约成功，发送短信通知，短信通知内容可以是“预约时间”，“预约人”，“预约地点”，“预约事项”等信息。
                String orderDate = (String) map.get("orderDate");
//                SMSUtils.sendShortMessage(telephone, "6666");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/findById")
    public Result findById(Integer id){
        Map map = null;
        try {
            map = orderService.findById4Detail(id);
            return Result.ok().data(map).message(MessageConstant.QUERY_ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
