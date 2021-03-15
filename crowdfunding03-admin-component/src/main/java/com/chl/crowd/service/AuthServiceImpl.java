package com.chl.crowd.service;

import com.chl.crowd.entity.Auth;
import com.chl.crowd.entity.AuthExample;
import com.chl.crowd.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
