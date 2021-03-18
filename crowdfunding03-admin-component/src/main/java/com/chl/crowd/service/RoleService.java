package com.chl.crowd.service;

import com.chl.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    public PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);

    List<Role> geAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);


}
