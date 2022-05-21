package com.hongyi.dao;

import com.hongyi.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
