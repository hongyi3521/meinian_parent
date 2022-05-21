package com.hongyi.dao;

import com.github.pagehelper.Page;
import com.hongyi.pojo.TravelGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);
    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(@Param("value") String queryString);

    TravelGroup findById(Integer id);

    List<Integer> getTravelItemIds(Integer id);

    void update(TravelGroup travelGroup);

    void delete(Integer id);

    void deleteById(Integer id);

    List<TravelGroup> findAll();

}
