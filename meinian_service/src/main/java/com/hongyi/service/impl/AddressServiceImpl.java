package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hongyi.dao.AddressDao;
import com.hongyi.entity.PageResult;
import com.hongyi.pojo.Address;
import com.hongyi.pojo.vo.QueryPageVo;
import com.hongyi.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Override
    public PageResult findPage(QueryPageVo queryPageVo) {
        // 当前页和每页数量
        PageHelper.startPage(queryPageVo.getCurrentPage(),queryPageVo.getPageSize());
        Page<Address> page = addressDao.selectByCondition(queryPageVo.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById(id);
    }
}
