package com.hongyi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyi.constant.MessageConstant;
import com.hongyi.constant.RedisConstant;
import com.hongyi.entity.PageResult;
import com.hongyi.entity.Result;
import com.hongyi.pojo.Setmeal;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.SetmealService;
import com.hongyi.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    // imgFile:需要跟页面el-upload里面的name保持一致
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            // 获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 截取字符串，.最后出现的位置
            int lastIndexOf = originalFilename.lastIndexOf(".");
            // 获取文件后缀，即.位置以后的就是后缀
            String suffix = originalFilename.substring(lastIndexOf);
            // 使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //图片上传成功
            fileName = "http://rbyw49sd5.hn-bkt.clouddn.com/" + fileName;
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return Result.ok().data("fileName", fileName).message(MessageConstant.PIC_UPLOAD_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(Integer[] travelgroupIds,
                      @RequestBody Setmeal setmeal) {
        try {
            setmealService.add(travelgroupIds, setmeal);
            return Result.ok().message(MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageVo queryPageVo) {
        PageResult pageResult = setmealService.findPage(queryPageVo);
        return pageResult;
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        Map<String, Object> map = setmealService.findById(id);
        return Result.ok().data(map);
    }
    @PostMapping("/update")
    public Result update(Integer[] travelgroupIds,
                         @RequestBody Setmeal setmeal){
        try {
            setmealService.update(travelgroupIds,setmeal);
            return Result.ok().message(MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error().message(MessageConstant.EDIT_SETMEAL_FAIL);
    }
    @GetMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.deleteById(id);
            return Result.ok().message(MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}
