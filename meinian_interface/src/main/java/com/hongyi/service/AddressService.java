package com.hongyi.service;

import com.hongyi.entity.PageResult;
import com.hongyi.pojo.Address;
import com.hongyi.pojo.vo.QueryPageVo;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    PageResult findPage(QueryPageVo queryPageVo);

    void addAddress(Address address);

    void deleteById(Integer id);
}
