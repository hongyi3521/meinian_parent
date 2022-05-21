package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyi.dao.OrdersettingDao;
import com.hongyi.pojo.OrderSetting;
import com.hongyi.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {
    @Autowired
    private OrdersettingDao ordersettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        // 1：遍历List<OrderSetting>，一个类表示一天预约日期
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 如果设置过预约日期，更新number数量,即修改表操作
            if (count > 0) {
                ordersettingDao.editNumberByOrderDate(orderSetting);
            } else {
                // 如果没有设置过预约日期，执行保存，表添加数据操作
                ordersettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) { // 2022-5
        // 根据日期查询预约设置数据
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        Map<String, Object> map = new HashMap<>();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        // 2.查询当前月份的预约设置,从1号到31号
        List<Map<String, Object>> data = new ArrayList<>();
        List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(map);
        // 3.将List<OrderSetting>，组织成List<Map>
        for (OrderSetting orderSetting : list) {
//            { date: 1, number: 120, reservations: 1 },
//            System.out.println(orderSetting.getOrderDate());
            int day = orderSetting.getOrderDate().getDate(); // 获取日期
            int number = orderSetting.getNumber(); // 可预约人数
            int reservations = orderSetting.getReservations(); // 已预约人数
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("date", day);
            orderMap.put("number", number);
            orderMap.put("reservations", reservations);
            data.add(orderMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate(Map map) {
        try {
            // 用map接收数据，取出来强转为String
            String dateSte = (String) map.get("date");
            // 将字符串转化为Date数据格式，需要先指定格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateSte);
            OrderSetting orderSetting = new OrderSetting();
            // 取出number，即预约人数
            String number = (String) map.get("number");
            // 字符串转化为Integer自动拆箱
            int i = Integer.parseInt(number);
            orderSetting.setNumber(i);
            orderSetting.setOrderDate(date);
            // 先查询有没有这个日期的数据
            long count = ordersettingDao.findCountByOrderDate(date);
            if (count > 0) { // 有这个日期就修改即可
                ordersettingDao.editNumberByOrderDate(orderSetting);
            } else { // 没有就重新添加，已预约人数int默认为0
                ordersettingDao.add(orderSetting);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
