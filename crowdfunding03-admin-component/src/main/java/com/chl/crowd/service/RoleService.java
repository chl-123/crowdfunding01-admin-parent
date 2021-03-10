package com.chl.crowd.service;

import com.chl.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
    public PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);
}
