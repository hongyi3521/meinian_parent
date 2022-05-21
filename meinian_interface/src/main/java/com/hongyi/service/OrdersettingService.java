package com.hongyi.service;

import com.hongyi.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map<String, Object>> getOrderSettingByMonth(String date);

    void editNumberByDate(Map map);
}
