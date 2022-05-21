package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.entity.PageResult;
import com.hongyi.entity.Result;
import com.hongyi.pojo.TravelItem;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference
    private TravelItemService travelItemService;

    @GetMapping("/findAll")
    public Result findAll(){
        List<TravelItem> list =  travelItemService.findAll();
        return Result.ok().data("list",list);
    }
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
            return Result.ok().message(MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            return Result.error().message(MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public PageResult findPage(@RequestBody QueryPageVo queryPageVo) {
        PageResult pageResult = travelItemService.findPage(queryPageVo);
        return pageResult;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result delete(Integer id) {
        try {
            travelItemService.deleteById(id);
            return Result.ok().message(MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e) {
            // 运行时异常，表示自由行和跟团游的关联表中存在数据
            return Result.error().message(e.getMessage());
        } catch (Exception e) {
            return Result.ok().message(MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        TravelItem travelItem = travelItemService.findById(id);
        return Result.ok().data("travelItem", travelItem);
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem) {
        travelItemService.edit(travelItem);
        return Result.ok().message(MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

}
