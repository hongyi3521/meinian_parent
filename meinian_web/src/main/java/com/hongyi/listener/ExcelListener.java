package com.hongyi.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hongyi.pojo.OrderSetting;
import com.hongyi.service.OrdersettingService;
import com.hongyi.vo.ExcelData;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<ExcelData> {
    private OrdersettingService ordersettingService;
    // 创建list集合封装最终的数据
    List<OrderSetting> list = new ArrayList<>();

    public ExcelListener() {
    }

    public ExcelListener(OrdersettingService ordersettingService) {
        this.ordersettingService = ordersettingService;
    }

    //一行一行去读取excle内容
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        OrderSetting orderSetting = new OrderSetting();
        orderSetting.setNumber(excelData.getNumber());
        orderSetting.setOrderDate(excelData.getOrderDate());
        list.add(orderSetting);
    }

    // 读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ordersettingService.add(list);
    }
}
