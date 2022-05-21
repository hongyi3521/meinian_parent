package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.entity.PageResult;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Address;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/findAllMaps")
    public Map findAll(){
        Map map=new HashMap();
        // 查出所有的地址信息
        List<Address> addressList = addressService.findAll();

        // 1、定义分店坐标集合
        List<Map> gridnMaps=new ArrayList<>();
        // 2、定义分店名称集合
        List<Map> nameMaps=new ArrayList();
        for (Address address : addressList) {
            Map gridnMap=new HashMap();
            // 获取经度
            gridnMap.put("lng",address.getLng());
            // 获取纬度
            gridnMap.put("lat",address.getLat());
            gridnMaps.add(gridnMap);

            Map nameMap=new HashMap();
            // 获取地址的名字
            nameMap.put("addressName",address.getAddressName());
            nameMaps.add(nameMap);
        }
        // 存放经纬度
        map.put("gridnMaps",gridnMaps);
        // 存放名字
        map.put("nameMaps",nameMaps);
        return map;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageVo queryPageVo){
        PageResult pageResult=null;
        try{
            pageResult= addressService.findPage(queryPageVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageResult;
    }

    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        // System.out.println(address.toString());
        addressService.addAddress(address);
        return Result.ok().message("地址保存成功");
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        addressService.deleteById(id);
        return Result.ok().message("已删除地址");
    }
}
