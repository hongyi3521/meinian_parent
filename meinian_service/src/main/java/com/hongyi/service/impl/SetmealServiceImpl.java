package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hongyi.constant.RedisConstant;
import com.hongyi.dao.SetmealDao;
import com.hongyi.dao.TravelGroupDao;
import com.hongyi.dao.TravelItemDao;
import com.hongyi.entity.PageResult;
import com.hongyi.pojo.Setmeal;
import com.hongyi.pojo.TravelGroup;
import com.hongyi.pojo.TravelItem;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private TravelGroupDao travelGroupDao;
    @Autowired
    private TravelItemDao travelItemDao;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        // 1.向t_setmeal表中添加数据
        setmealDao.add(setmeal);
        // 主键回填获取id
        Integer setmealId = setmeal.getId();
        // 提炼方法
        setSetmealAndTravelGroup(setmealId,travelgroupIds);
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageVo queryPageVo) {
        Integer currentPage = queryPageVo.getCurrentPage();
        Integer pageSize = queryPageVo.getPageSize();
        String queryString = queryPageVo.getQueryString();
        // 使用工具，省略步骤
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Map<String,Object> findById(Integer id) {
        // 先修改套餐游数据
        Setmeal setmeal = setmealDao.findById(id);
        List<Integer> travelGroupIds = setmealDao.getTravelGroupIds(id);
        Map<String,Object> map = new HashMap<>();
        map.put("setmeal",setmeal);
        map.put("travelGroupIds",travelGroupIds);
        return map;
    }

    @Override
    public void update(Integer[] travelgroupIds, Setmeal setmeal) {
        // 先修改数据库的套餐游详情
        setmealDao.update(setmeal);
        Integer setmealId = setmeal.getId();
        // 先删除关联表中数据，再添加
        setmealDao.delete(setmealId);
        setSetmealAndTravelGroup(setmealId,travelgroupIds);
    }

    @Override
    public void deleteById(Integer id) {
        // 先删除表关联数据
        setmealDao.delete(id);
        // 删除setmeal表数据
        setmealDao.deleteById(id);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal getSetmealById(Integer id) {
        // 先根据id查出详细信息
        Setmeal setmeal = setmealDao.findById(id);
        // 每个套餐游对应多个跟团游
        List<Integer> travelGroupIds = setmealDao.getTravelGroupIds(id);
        List<TravelGroup> travelGroupList = new ArrayList<>();
        for(Integer travelGroupId:travelGroupIds){
            // 得到跟团游详细信息
            TravelGroup travelGroup = travelGroupDao.findById(travelGroupId);
            // 每个跟团游对应多个自由行
            List<TravelItem> travelItemList = findTravelGroupListById(travelGroupId);
            travelGroup.setTravelItems(travelItemList);
            travelGroupList.add(travelGroup);
        }
        setmeal.setTravelGroups(travelGroupList);
        return setmeal;
    }

    @Override
    public Setmeal findSetmealById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    // 根据跟团游id得到关联的自由行数据集合
    private List<TravelItem> findTravelGroupListById(Integer id) {
        List<Integer> travelItemIds = travelGroupDao.getTravelItemIds(id);
        List<TravelItem> travelItemList = new ArrayList<>();
        for(Integer travelItemId: travelItemIds){
            TravelItem travelItem = travelItemDao.findById(travelItemId);
            travelItemList.add(travelItem);
        }
        return travelItemList;
    }

    // 将图片名称保存到Redis
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }
    // 绑定套餐和跟团游的多对多关系
    public void setSetmealAndTravelGroup(Integer setmealId,Integer[] travelgroupIds){
        // 先验证数组
        if(travelgroupIds.length>0 && travelgroupIds !=null){
            for(Integer travelgroupId : travelgroupIds){
                Map<String,Integer> map = new HashMap<>();
                map.put("travelgroupId",travelgroupId);
                map.put("setmealId",setmealId);
                setmealDao.setSetmealAndTravelGroup(map);
            }
        }
    }
}
