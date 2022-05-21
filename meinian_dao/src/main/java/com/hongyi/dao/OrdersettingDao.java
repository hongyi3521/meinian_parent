package com.hongyi.dao;

import com.hongyi.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, Object> map);
    // 根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date date);
    // 更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
