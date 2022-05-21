package com.hongyi.vo;


import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class ExcelData {
    @ExcelProperty(index = 0)
    private Date orderDate;//预约设置日期
    @ExcelProperty(index = 1)
    private int number;//可预约人数

    public ExcelData() {
    }

    public ExcelData(Date orderDate, int number) {
        this.orderDate = orderDate;
        this.number = number;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "OrdersettingData{" +
                "orderDate=" + orderDate +
                ", number=" + number +
                '}';
    }
}
