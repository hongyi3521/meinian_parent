package com.hongyi.service;

import com.hongyi.entity.Result;
import com.hongyi.pojo.Order;

import java.util.Map;

public interface OrderService {
    Result submitOrder(Map map) throws Exception;
    // 根据id查询预约信息，包括人信息、套餐信息
    Map findById4Detail(Integer id) throws Exception;
}
