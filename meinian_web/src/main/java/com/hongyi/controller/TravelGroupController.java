package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.entity.PageResult;
import com.hongyi.entity.Result;
import com.hongyi.pojo.TravelGroup;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.TravelGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<TravelGroup> list = travelGroupService.findAll();
        if (list.size() > 0 && list != null) {
            return Result.ok().data("list", list);
        }
        return Result.ok().message(MessageConstant.QUERY_SETMEAL_FAIL);
    }

    // 前台传过来的除了跟团游数据，还有选择的自由行id集合
    @PostMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupService.add(travelGroup, travelItemIds);
            return Result.ok().message(MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok().message(MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageVo queryPageVo) {
        PageResult pageResult = travelGroupService.findPage(queryPageVo);
        return pageResult;
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        Map<String, Object> map = travelGroupService.findById(id);
        return Result.ok().data(map);
    }

    @PostMapping("/update")
    public Result update(Integer[] travelItemIds,
                         @RequestBody TravelGroup travelGroup) {
        try {
            travelGroupService.update(travelGroup, travelItemIds);
            return Result.ok().message(MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok().message(MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        try {
            travelGroupService.deleteById(id);
            return Result.ok().message(MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            return Result.ok().message(MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }
}
