package com.hongyi.dao;

import com.github.pagehelper.Page;
import com.hongyi.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndTravelGroup(Map<String, Integer> map);

    Page<Setmeal> findPage(@Param("value") String queryString);

    Setmeal findById(Integer id);

    List<Integer> getTravelGroupIds(Integer id);

    void delete(Integer setmealId);

    void update(Setmeal setmeal);

    void deleteById(Integer id);

    List<Setmeal> findAll();

    List<Map<String, Object>> findSetmealCount();
}
