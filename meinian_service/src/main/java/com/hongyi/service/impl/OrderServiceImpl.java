package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyi.constant.MessageConstant;
import com.hongyi.dao.MemberDao;
import com.hongyi.dao.OrderDao;
import com.hongyi.dao.OrdersettingDao;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Member;
import com.hongyi.pojo.Order;
import com.hongyi.pojo.OrderSetting;
import com.hongyi.service.OrderService;
import com.hongyi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result submitOrder(Map map) throws Exception {
        // 分析，提交预约，需要把用户信息写进数据库
        // 需要查询预约的当天人数是否满，需要修改表数据
        // 需要将生成订单信息插入数据
        // 前端需要把生成的订单信息展示，所以需要返回订单信息

        // 1.查询当天有没有预约,人数是否满
        // 因为数据库预约设置表里面的时间是date类型，http协议传递的是字符串类型，所以需要转换
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate, "yyyy-MM-dd");
        // 使用预约时间查询预约设置表，看看是否可以 进行预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        // 如果预约设置表等于null，说明不能进行预约，压根就没有开团
        if (orderSetting == null) {
            // 所选日期不能进行旅游预约
            return Result.error().message(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSetting.getNumber();// 可预约人数
        int reservations = orderSetting.getReservations();// 已预约人数
        if (reservations >= number) {
            // 预约已满
            return Result.error().message(MessageConstant.ORDER_FULL);
        }

        // 2 使用手机号，查询会员表，判断当前预约人是否是会员，不是会员注册会员
        String telephone = (String) map.get("telephone");
        // 查询当用户是否已经注册，没注册就insert
        Member member = memberDao.findByTelephone(telephone);
        // 已经注册，查询是否已经预约
        if (member != null) {
            Integer memberId = member.getId();
            // 获取套餐id
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            // 将订单信息集合，即用户id，时间，套餐id，同一人，同一天，同一套餐，不可重复
            Order order = new Order(memberId, date, null, null, setmealId);
            List<Order> list = orderDao.findByCondition(order);
            // 不等于null说明那天有预约活动，>0说明客户之前没有预约
            if (list != null && list.size() > 0) {
                // 已经完成预约，不能重复预约
                return Result.error().message(MessageConstant.HAS_ORDERED);
            }
        } else { // 没注册，即之前没有预约信息
            // 向会员表中添加数据
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            // 这里设置主键回填
            memberDao.add(member);
        }

        // 3 可以进行预约，更新预约设置表中预约人数的值，使其的值+1
        // 可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        // orderSetting里有日期，有已预约数量
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        // 4可以进行预约，向预约表中添加1条数据
        // 保存预约信息到预约表
        Order order = new Order();
        order.setMemberId(member.getId()); //会员id
        order.setOrderDate(date); // 预约时间
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 预约状态（已出游/未出游）
        order.setOrderType((String) map.get("orderType"));
        int setmealId = Integer.parseInt((String) map.get("setmealId"));
        order.setSetmealId(setmealId);
        orderDao.add(order);
        return Result.ok().data("order", order).message(MessageConstant.ORDER_SUCCESS);
    }

    @Override
    public Map findById4Detail(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if (map != null) {
            //处理日期格式 2022-06-10 00:00:00
            // 数据库的日期格式查出需要更改格式适应前端页面
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
            return map;
        }
        return map;
    }

}
