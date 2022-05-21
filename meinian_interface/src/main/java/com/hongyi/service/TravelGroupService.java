package com.hongyi.service;

import com.hongyi.entity.PageResult;
import com.hongyi.pojo.TravelGroup;
import com.hongyi.pojo.vo.QueryPageVo;

import java.util.List;
import java.util.Map;

public interface TravelGroupService {
    void add(TravelGroup travelGroup,Integer[] travelItemIds);

    PageResult findPage(QueryPageVo queryPageVo);

    Map<String, Object> findById(Integer id);

    void update(TravelGroup travelGroup, Integer[] travelItemIds);

    void deleteById(Integer id);

    List<TravelGroup> findAll();
}
