package com.hongyi.service;

import com.hongyi.entity.PageResult;
import com.hongyi.pojo.Setmeal;
import com.hongyi.pojo.vo.QueryPageVo;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageVo queryPageVo);

    Map<String,Object> findById(Integer id);

    void update(Integer[] travelgroupIds, Setmeal setmeal);

    void deleteById(Integer id);

    List<Setmeal> findAll();

    Setmeal getSetmealById(Integer id);

    Setmeal findSetmealById(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
