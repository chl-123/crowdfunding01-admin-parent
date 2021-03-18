package com.chl.crowd.service;

import com.chl.crowd.entity.Auth;
import com.chl.crowd.entity.AuthExample;
import com.chl.crowd.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    AuthMapper authMapper;
    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        //获取roleID的值
        List<Integer> roleIdList=map.get("roleId");
        Integer roleId=roleIdList.get(0);
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList=map.get("authIdArray");
        if (authIdList != null&&authIdList.size()>0) {
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
