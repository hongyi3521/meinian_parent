package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hongyi.dao.TravelGroupDao;
import com.hongyi.dao.TravelItemDao;
import com.hongyi.entity.PageResult;
import com.hongyi.pojo.TravelGroup;
import com.hongyi.pojo.TravelItem;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.TravelGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;
    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        // 添加跟团游数据
        travelGroupDao.add(travelGroup);
        // 这里需要设置插入数据库后的主键回填、
        Integer travelGroupId = travelGroup.getId();
        // 将和跟团游关联的自由行数据插入关联表
        setTravelGroupAndTravelItem(travelGroupId, travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageVo queryPageVo) {
        Integer currentPage = queryPageVo.getCurrentPage();
        Integer pageSize = queryPageVo.getPageSize();
        String queryString = queryPageVo.getQueryString();
        // 1：初始化分页操作
        PageHelper.startPage(currentPage, pageSize);
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<TravelGroup> page = travelGroupDao.findPage(queryString);
        // 3：封装,总数据条数，查询结果集
        return new PageResult(page.getTotal(), page.getResult());
    }


    public void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        // 1.验证
        if (travelItemIds != null && travelItemIds.length > 0) {
            // 新增几条数据，由travelItemIds的长度决定
            for (Integer travelItemId : travelItemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroupId", travelGroupId);
                map.put("travelItemId", travelItemId);
                // 关联表的数据页这个dao
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        // 1.根据id查出跟团游详细信息
        TravelGroup travelGroup = travelGroupDao.findById(id);
        // 2.根据id查出关联表中的自由行数据
        List<Integer> travelItemIds = travelGroupDao.getTravelItemIds(id);
        Map<String, Object> map = new HashMap<>();
        map.put("travelGroup", travelGroup);
        map.put("travelItemIds", travelItemIds);
        return map;
    }

    @Override
    public void update(TravelGroup travelGroup, Integer[] travelItemIds) {
        // 修改数据
        travelGroupDao.update(travelGroup);
        // 先把和travelGroupId关联的数据删除，再添加
        travelGroupDao.delete(travelGroup.getId());
        Integer travelGroupId = travelGroup.getId();
        setTravelGroupAndTravelItem(travelGroupId, travelItemIds);
    }

    @Override
    public void deleteById(Integer id) {
        // 先删除关联数据
        travelGroupDao.delete(id);
        // 再删除详细数据
        travelGroupDao.deleteById(id);
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

}
