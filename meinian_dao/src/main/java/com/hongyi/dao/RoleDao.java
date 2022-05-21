package com.hongyi.dao;

import com.hongyi.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}
