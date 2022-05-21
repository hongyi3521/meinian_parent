package com.hongyi.service;

import com.hongyi.entity.PageResult;
import com.hongyi.pojo.TravelItem;
import com.hongyi.pojo.vo.QueryPageVo;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageVo queryPageVo);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
