package com.chl.crowd.service;

import com.chl.crowd.entity.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);
}
