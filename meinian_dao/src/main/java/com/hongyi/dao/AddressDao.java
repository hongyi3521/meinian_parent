package com.hongyi.dao;

import com.github.pagehelper.Page;
import com.hongyi.pojo.Address;

import java.util.List;

public interface AddressDao {
    List<Address> findAll();

    void addAddress(Address address);

    Page<Address> selectByCondition(String queryString);

    void deleteById(Integer id);
}
