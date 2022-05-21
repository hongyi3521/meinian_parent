package com.hongyi.dao;

import com.github.pagehelper.Page;
import com.hongyi.pojo.TravelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem) ;

    Page<TravelItem> findPage(@Param("value") String queryString);

    long findCountByTravelItemItemId(Integer id);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
