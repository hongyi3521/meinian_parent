package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.hongyi.constant.MessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.listener.ExcelListener;
import com.hongyi.pojo.OrderSetting;
import com.hongyi.service.OrdersettingService;
import com.hongyi.utils.POIUtils;
import com.hongyi.vo.ExcelData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {
    @Reference
    private OrdersettingService ordersettingService;

    //    @RequestMapping("/upload")
//    public Result upload(MultipartFile excelFile) {
//        try {
//            // 使用poi工具类解析excel文件，读取里面的内容,list里存每一行。数组里存每一列
//            List<String[]> lists = POIUtils.readExcel(excelFile);
//            // 把List<String[]> 数据转换成 List<OrderSetting>数据
//            List<OrderSetting> orderSettings = new ArrayList<>();
//            //  迭代里面的每一行数据，进行封装到集合里面
//            for (String[] str : lists) {
//                // 获取到一行里面，每个表格数据，进行封装
//                OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
//                orderSettings.add(orderSetting);
//            }
//            // 调用业务进行保存
//            ordersettingService.add(orderSettings);
//            return Result.ok().message(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error().message(MessageConstant.IMPORT_ORDERSETTING_FAIL);
//        }
//    }
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            InputStream inputStream = excelFile.getInputStream();
            EasyExcel.read(inputStream, ExcelData.class, new ExcelListener(ordersettingService))
                    .sheet().doRead();
            return Result.ok().message(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
//    根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map<String,Object>> leftobj = ordersettingService.getOrderSettingByMonth(date);
            return Result.ok().data("leftobj",leftobj).message(MessageConstant.GET_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok().message(MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Map map){// 可以用Ordersetting接收数据，也可以用map键值对
        try {
            ordersettingService.editNumberByDate(map);
            return Result.ok().message(MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
