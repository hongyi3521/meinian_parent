package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Setmeal;
import com.hongyi.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    @PostMapping("/getSetmeals")
    public Result getSetmeals(){
        try {
            List<Setmeal> list = setmealService.findAll();
            return Result.ok().data("list",list).message(MessageConstant.QUERY_SETMEALLIST_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }
    // 根据id查询套餐信息
    @PostMapping("/getSetmealById")
    public Result getSetmealById(Integer id){
        try {
            Setmeal setmeal = setmealService.getSetmealById(id);
            return Result.ok().data("setmeal",setmeal).message(MessageConstant.QUERY_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @GetMapping("/findSetmealById")
    public Result findSetmealById(Integer id){
        try {
            Setmeal setmeal = setmealService.findSetmealById(id);
            return Result.ok().data("setmeal",setmeal).message(MessageConstant.QUERY_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
